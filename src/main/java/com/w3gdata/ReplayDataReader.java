package com.w3gdata;

import com.w3gdata.util.ByteUtils;

public class ReplayDataReader {
    private final W3gInfo data;
    private final byte[] buf;
    private int offset;

    public ReplayDataReader(W3gInfo data, byte[] buf, int offset) {
        this.data = data;
        this.buf = buf;
        this.offset = offset;
    }

    public void read() {
        //todo continued...
    }

    private void readLeaveGameRecord() {
        data.leaveGameRecord.reason = readDWord();
        data.leaveGameRecord.playerId = buf[offset++];
        data.leaveGameRecord.result = readDWord();
        offset += 4;
    }

    private int readDWord() {
        offset += 4;
        return ByteUtils.readDWord(buf, offset - 4);
    }

    private String readNullTerminatedString() {
        String result = ByteUtils.readNullTerminatedString(buf, offset);
        offset += result.length() + 1;
        return result;
    }
}
