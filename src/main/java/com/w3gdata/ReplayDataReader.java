package com.w3gdata;

import static com.w3gdata.ReplayDataFormat.*;

import com.w3gdata.util.ByteBuffer;

public class ReplayDataReader {
    private final W3gInfo data;
    private final ByteBuffer buf;

    public ReplayDataReader(W3gInfo data, ByteBuffer buf) {
        this.data = data;
        this.buf = buf;
    }

    public void read() {
        while(buf.hasNext()) {
            ReplayDataFormat replayDataFormat = getFormatByCode(buf.peek());
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

    private TimeBlock readTimeBlock() {
        TimeBlock timeBlock = new TimeBlock();
        timeBlock.n = buf.readWord();
        timeBlock.timeIncrement = buf.readWord();
        byte[] commandDataBlocks = buf.readBytes(timeBlock.n - 2);

        return timeBlock;
    }
}
