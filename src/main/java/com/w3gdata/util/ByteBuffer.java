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

    public byte[] readBytes(int n) {
        byte[] result = new byte[n];
        for (int i = 0; i < n; i++) {
            result[i] = buf[offset++];
        }
        return result;
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

    public int readWord() {
        offset += 2;
        return ByteUtils.readWord(buf, offset - 2);
    }

    public int findNullTermination() {
        int i = offset;
        while (buf[i++] != 0) ;
        return i - 1;
    }


    public String readNullTerminatedString() {
        String result = ByteUtils.readNullTerminatedString(buf, offset);
        offset += result.length() + 1;
        return result;
    }

    public boolean hasNext() {
        return offset < buf.length;
    }

    public void debugWhatIsLeftToFile() {
        ByteUtils.debugToFile(getBuf(),
                getOffset(),
                getBuf().length - getOffset(),
                "decompressed.bin");
    }
}
