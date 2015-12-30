package com.w3gdata;

import static com.w3gdata.ReplayDataFormat.*;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.w3gdata.actionblock.ActionBlock;
import com.w3gdata.actionblock.ActionBlockFormat;
import com.w3gdata.util.ByteBuffer;
import org.apache.log4j.Logger;

public class ReplayDataReader {

    private static final Logger logger = Logger.getLogger(ReplayDataReader.class);

    private final W3gInfo data;
    private final ByteBuffer buf;

    public ReplayDataReader(W3gInfo data, ByteBuffer buf) {
        this.data = data;
        this.buf = buf;
        buf.debugWhatIsLeftToFile();
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

    private TimeSlotBlock readTimeBlock() {
        TimeSlotBlock timeSlotBlock = new TimeSlotBlock();
        timeSlotBlock.n = buf.readWord();
        timeSlotBlock.timeIncrement = buf.readWord();
        if (timeSlotBlock.n != 2) {
            //Please do not approach!
            timeSlotBlock.commandDataBlocks =
                    readCommandDataBlocks(buf.getOffset() + timeSlotBlock.n - 2);
        }
        return timeSlotBlock;
    }

    private Multimap<Byte, CommandData> readCommandDataBlocks(int limit) {
        Multimap<Byte, CommandData> commandDataBlocks = ArrayListMultimap.create();
        while (buf.getOffset() < limit) {
            CommandData commandData = new CommandData();
            commandData.playerId = buf.readByte();
            commandData.actionBlockLength = buf.readWord();
            commandData.actionBlocks = readActionBlocks(commandData.actionBlockLength + buf.getOffset());
        }
        return commandDataBlocks;
    }

    private Multimap<Byte, ActionBlock> readActionBlocks(int limit) {
        Multimap<Byte, ActionBlock> actionBlocks = ArrayListMultimap.create();
        while (buf.getOffset() < limit) {
            byte id = buf.readByte();
            actionBlocks.put(id, ActionBlockFormat.getById(id).process(buf));
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
