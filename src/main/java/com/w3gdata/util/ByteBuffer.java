package com.w3gdata.util;

public class ByteBuffer {

    private final byte[] buf;
    private int offset;

    public ByteBuffer(byte[] buf, int offset) {
        this.buf = buf;
        this.offset = offset;
    }

    public void increment(int i) {
        offset += i;
    }

    public byte peek() {
        return buf[offset];
    }

    public byte readByte() {
        return buf[offset++];
    }

    public byte[] getBuf() {
        return buf;
    }

    public int getOffset() {
        return offset;
    }

    public int readDWord() {
        offset += 4;
        return ByteUtils.readDWord(buf, offset - 4);
    }

    public int findNullTermination() {
        int i = offset;
        while(buf[i++] != 0);
        return i - 1;
    }


    public String readNullTerminatedString() {
        String result = ByteUtils.readNullTerminatedString(buf, offset);
        offset += result.length() + 1;
        return result;
    }
}
