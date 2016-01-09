package com.w3gdata.parser;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.w3gdata.parser.action.Action;
import com.w3gdata.parser.action.Actions;
import com.w3gdata.util.ByteBuffer;
import com.w3gdata.util.ByteUtils;
import org.apache.log4j.Logger;

import static com.w3gdata.parser.ReplayDataFormat.*;

public class ReplayDataReader {

    private static final Logger logger = Logger.getLogger(ReplayDataReader.class);

    private final W3gInfo data;
    private final ByteBuffer buf;

    public ReplayDataReader(W3gInfo data, ByteBuffer buf) {
        this.data = data;
        this.buf = buf;
    }

    public void read() {
        try {
            while(buf.hasNext()) {
                int replayDataId = buf.readByte();
                ReplayDataFormat replayDataFormat = getById(replayDataId);
                if (replayDataFormat != null) {
                    if (replayDataFormat.isKnown) {
                        processBlockByFormat(replayDataFormat);
                    } else {
                        buf.increment(replayDataFormat.fixedSize - 1);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Failed on offset: " + buf.getOffset());
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
        leaveGameRecord.reason = buf.readDWord();
        leaveGameRecord.playerId = buf.readByte();
        leaveGameRecord.result = buf.readDWord();
        buf.increment(4);
        return leaveGameRecord;
    }

    private ForcedGameEndCountdownRecord readForcedGameEndCountdownRecord() {
        ForcedGameEndCountdownRecord forcedCountdown = new ForcedGameEndCountdownRecord();
        forcedCountdown.mode = buf.readDWord();
        forcedCountdown.countdown = buf.readDWord();
        return forcedCountdown;
    }

    private TimeSlot readTimeBlock() {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.n = buf.readWord();
        timeSlot.timeIncrement = buf.readWord();
        if (timeSlot.n != 2) {
            //Please do not approach!
            timeSlot.commandDataBlocks =
                    readCommandDataBlocks(buf.getOffset() + timeSlot.n - 2);
        }
        return timeSlot;
    }

    private Multimap<Byte, Command> readCommandDataBlocks(int limit) {
        Multimap<Byte, Command> commandDataBlocks = ArrayListMultimap.create();
        while (buf.getOffset() < limit) {
            Command command = new Command();
            command.playerId = buf.readByte();
            command.actionBlockLength = buf.readWord();
            command.actionBlocks = readActionBlocks(command.actionBlockLength + buf.getOffset());
            commandDataBlocks.put(command.playerId, command);
        }
        return commandDataBlocks;
    }

    private Multimap<Byte, Action> readActionBlocks(int limit) {
        Multimap<Byte, Action> actionBlocks = ArrayListMultimap.create();
        while (buf.getOffset() < limit) {
            int id = buf.readByte();
            Action action = Actions.getById(id).shape.deserialize(buf);
            actionBlocks.put((byte) action.getId(), action);
        }
        return actionBlocks;
    }

    private PlayerChatMessage readPlayerChatMessage() {
        PlayerChatMessage result = new PlayerChatMessage();
        result.playerId = buf.readByte();
        result.n = buf.readWord();
        result.flag = buf.readByte();
        result.chatMode = buf.readDWord();
        result.message = buf.readNullTerminatedString();
        return result;
    }
}
