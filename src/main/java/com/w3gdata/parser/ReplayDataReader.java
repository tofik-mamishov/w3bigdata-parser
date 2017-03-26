package com.w3gdata.parser;

import static com.w3gdata.parser.ReplayDataFormat.FORCED_GAME_END_COUNTDOWN;
import static com.w3gdata.parser.ReplayDataFormat.LEAVE_GAME;
import static com.w3gdata.parser.ReplayDataFormat.PLAYER_CHAT_MESSAGE;
import static com.w3gdata.parser.ReplayDataFormat.TIME_SLOT_BLOCK_NEW;
import static com.w3gdata.parser.ReplayDataFormat.getById;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.w3gdata.parser.action.Action;
import com.w3gdata.parser.action.Actions;
import com.w3gdata.util.ByteReader;
import com.w3gdata.util.ByteUtils;
import org.apache.log4j.Logger;

public class ReplayDataReader {

    private static final Logger logger = Logger.getLogger(ReplayDataReader.class);

    private final W3gInfo data;
    private final ByteReader buf;

    public ReplayDataReader(W3gInfo data, ByteReader buf) {
        this.data = data;
        this.buf = buf;
    }

    public void read() {
        try {
            while (buf.hasMore()) {
                int replayDataId = buf.nextByte();
                ReplayDataFormat replayDataFormat = getById(replayDataId);
                if (replayDataFormat != null) {
                    if (replayDataFormat.isKnown) {
                        processBlockByFormat(replayDataFormat);
                    } else {
                        buf.forward(replayDataFormat.fixedSize - 1);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Failed on offset: " + buf.offset());
            logger.error(e.getMessage(), e);
            ByteUtils.debugToFile(buf.getBuf(), "full.bin");
            throw new W3gParserException(e);
        }
    }

    private void processBlockByFormat(ReplayDataFormat replayDataFormat) {
        if (replayDataFormat == LEAVE_GAME) {
            data.leaveGameRecords.add(readLeaveGameRecord());
        } else if (replayDataFormat == FORCED_GAME_END_COUNTDOWN) {
            data.forcedGameEndCountdownRecords.add(readForcedGameEndCountdownRecord());
        } else if (replayDataFormat == TIME_SLOT_BLOCK_NEW) {
            data.timeSlots.add(readTimeBlock());
        } else if (replayDataFormat == PLAYER_CHAT_MESSAGE) {
            data.playerChatMessages.add(readPlayerChatMessage());
        }
    }

    private LeaveGameRecord readLeaveGameRecord() {
        LeaveGameRecord leaveGameRecord = new LeaveGameRecord();
        leaveGameRecord.reason = buf.nextDWord();
        leaveGameRecord.playerId = buf.nextByte();
        leaveGameRecord.result = buf.nextDWord();
        buf.forward(4);
        return leaveGameRecord;
    }

    private ForcedGameEndCountdownRecord readForcedGameEndCountdownRecord() {
        ForcedGameEndCountdownRecord forcedCountdown = new ForcedGameEndCountdownRecord();
        forcedCountdown.mode = buf.nextDWord();
        forcedCountdown.countdown = buf.nextDWord();
        return forcedCountdown;
    }

    private TimeSlot readTimeBlock() {
        TimeSlot timeSlot = new TimeSlot(buf);
        if (timeSlot.n != 2) {
            timeSlot.commandDataBlocks.putAll(readCommandDataBlocks(buf.offset() + timeSlot.n - 2));
        }
        return timeSlot;
    }

    private Multimap<Byte, Command> readCommandDataBlocks(int limit) {
        Multimap<Byte, Command> commands = ArrayListMultimap.create();
        while (buf.offset() < limit) {
            Command command = new Command(buf);
            command.actionBlocks.putAll(readActionBlocks(command.actionBlockLength + buf.offset()));
            commands.put(command.playerId, command);
        }
        return commands;
    }

    private Multimap<Byte, Action> readActionBlocks(int limit) {
        Multimap<Byte, Action> actionBlocks = ArrayListMultimap.create();
        while (buf.offset() < limit) {
            int id = buf.nextByte();
            Action action = Actions.getById(id).shape.deserialize(buf);
            actionBlocks.put((byte) action.getId(), action);
        }
        return actionBlocks;
    }

    private PlayerChatMessage readPlayerChatMessage() {
        PlayerChatMessage result = new PlayerChatMessage();
        result.playerId = buf.nextByte();
        result.n = buf.nextWord();
        result.flag = buf.nextByte();
        result.chatMode = buf.nextDWord();
        result.message = buf.nextNullTerminatedString();
        return result;
    }
}
