package com.w3gdata;

import static com.w3gdata.ReplayDataFormat.*;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.w3gdata.actionblock.ActionBlock;
import com.w3gdata.actionblock.ActionBlockFormat;
import com.w3gdata.util.ByteReader;
import org.apache.log4j.Logger;

public class ReplayDataReader {

    private static final Logger logger = Logger.getLogger(ReplayDataReader.class);

    private final W3gInfo data;
    private final ByteReader buf;

    public ReplayDataReader(W3gInfo data, ByteReader buf) {
        this.data = data;
        this.buf = buf;
        buf.debugWhatIsLeftToFile();
    }

    public void read() {
        try {
            while(buf.hasMore()) {
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
            logger.error(e.getMessage(), e);
//            buf.debugWhatIsLeftToFile();
            throw new W3gParserException(e);
        }
    }

    private void processBlockByFormat(ReplayDataFormat replayDataFormat) {
        if (replayDataFormat == LEAVE_GAME) {
            data.leaveGameRecords.add(readLeaveGameRecord());
        } else if (replayDataFormat == FORCED_GAME_END_COUNTDOWN) {
            data.forcedGameEndCountdownRecords.add(readForcedGameEndCountdownRecord());
        } else if (replayDataFormat == TIME_SLOT_BLOCK_NEW) {
            data.timeSlotBlocks.add(readTimeBlock());
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

    private TimeSlotBlock readTimeBlock() {
        TimeSlotBlock timeSlotBlock = new TimeSlotBlock();
        timeSlotBlock.n = buf.nextWord();
        timeSlotBlock.timeIncrement = buf.nextWord();
        if (timeSlotBlock.n != 2) {
            //Please do not approach!
            timeSlotBlock.commandDataBlocks =
                    readCommandDataBlocks(buf.offset() + timeSlotBlock.n - 2);
        }
        return timeSlotBlock;
    }

    private Multimap<Byte, CommandData> readCommandDataBlocks(int limit) {
        Multimap<Byte, CommandData> commandDataBlocks = ArrayListMultimap.create();
        while (buf.offset() < limit) {
            CommandData commandData = new CommandData();
            commandData.playerId = buf.nextByte();
            commandData.actionBlockLength = buf.nextWord();
            commandData.actionBlocks = readActionBlocks(commandData.actionBlockLength + buf.offset());
        }
        return commandDataBlocks;
    }

    private Multimap<Byte, ActionBlock> readActionBlocks(int limit) {
        Multimap<Byte, ActionBlock> actionBlocks = ArrayListMultimap.create();
        while (buf.offset() < limit) {
            byte id = buf.nextByte();
            actionBlocks.put(id, ActionBlockFormat.getById(id).process(buf));
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
