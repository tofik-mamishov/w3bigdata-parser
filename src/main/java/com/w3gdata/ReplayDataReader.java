package com.w3gdata;

import com.w3gdata.util.ByteBuffer;

public class ReplayDataReader {
    private final W3gInfo data;
    private final ByteBuffer buf;

    public ReplayDataReader(W3gInfo data, ByteBuffer buf) {
        this.data = data;
        this.buf = buf;
    }

    public void read() {
        //todo continued...
    }

    private void readLeaveGameRecord() {
        data.leaveGameRecord.reason = buf.readDWord();
        data.leaveGameRecord.playerId = buf.readByte();
        data.leaveGameRecord.result = buf.readDWord();
        buf.increment(4);
    }
}
