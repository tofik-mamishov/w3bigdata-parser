package com.w3gdata;

import static com.w3gdata.ReplayDataFormat.*;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.w3gdata.actionblock.ActionBlock;
import com.w3gdata.actionblock.ActionBlockFormat;
import com.w3gdata.util.ByteBuffer;
import com.w3gdata.util.ByteUtils;

public class ReplayDataReader {
    private final W3gInfo data;
    private final ByteBuffer buf;

    public ReplayDataReader(W3gInfo data, ByteBuffer buf) {
        this.data = data;
        this.buf = buf;
    }

    public void read() {
        while(buf.hasNext()) {
            ReplayDataFormat replayDataFormat = getById(buf.peek());
            if (replayDataFormat != null) {
                if (replayDataFormat.isKnown) {
                    processBlockByFormat(replayDataFormat);
                } else {
                    buf.increment(replayDataFormat.fixedSize);
                }
            }
        }
        //todo continued...
    }

    private void processBlockByFormat(ReplayDataFormat replayDataFormat) {
        if (replayDataFormat == LEAVE_GAME) {
            data.leaveGameRecords.add(readLeaveGameRecord());
        } else if (replayDataFormat == FORCED_GAME_END_COUNTDOWN) {
            data.forcedGameEndCountdownRecords.add(readForcedGameEndCountdownRecord());
        } else if (replayDataFormat == TIME_SLOT_BLOCK_OLD || replayDataFormat == TIME_SLOT_BLOCK_NEW) {

        } else if (replayDataFormat == PLAYER_CHAT_MESSAGE) {

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
        byte[] commandDataBytes = buf.readBytes(timeSlotBlock.n - 2);

        return timeSlotBlock;
    }

    private Multimap<Byte, CommandData> readCommandDataBlocks(byte[] commandDataBytes) {
        Multimap<Byte, CommandData> commandDataBlocks = ArrayListMultimap.create();
        for (int i = 0; i < commandDataBytes.length; i++) {
            CommandData commandData = new CommandData();
            commandData.playerId = commandDataBytes[i++];
            commandData.actionBlockLength = ByteUtils.readWord(commandDataBytes, i);
            commandData.actionBlocks = readActionBlocks(
                    ByteUtils.readBytes(commandDataBytes, i, commandData.actionBlockLength)
            );
        }
        return commandDataBlocks;
    }

    private Multimap<Integer, ActionBlock> readActionBlocks(byte[] actionBlocksBytes) {
        Multimap<Integer, ActionBlock> actionBlocks = ArrayListMultimap.create();
        for (int i = 0; i < actionBlocksBytes.length; i++) {
            int id = actionBlocksBytes[i++];
            //ActionBlockFormat.getTypeById(id);
            //todo action blocks parsing. Maybe we need to specify concrete types?
        }
        return actionBlocks;
    }
}
