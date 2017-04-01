package com.w3gdata.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ByteReader {

    private final byte[] buf;
    private int offset;

    public ByteReader(byte[] buf, int offset) {
        this.buf = buf;
        this.offset = offset;
    }

    public ByteReader(byte[] buf) {
        this(buf, 0);
    }

    public void forward(int i) {
        offset += i;
    }

    public byte peek() {
        return buf[offset];
    }

    public byte nextByte() {
        return buf[offset++];
    }

    public int nextByteAsInt() {
        return (int)buf[offset++];
    }

    public byte[] nextBytes(int n) {
        byte[] result = new byte[n];
        for (int i = 0; i < n; i++) {
            result[i] = buf[offset++];
        }
        return result;
    }

    public String nextBytesAsString(int n) {
        byte[] result = new byte[n];
        for (int i = n - 1; i >= 0; i--) {
            result[i] = buf[offset++];
        }
        return new String(result);
    }

    public byte[] getBuf() {
        return buf;
    }

    public int total() {
        return buf.length;
    }

    public int left() {
        return total() - offset();
    }

    public int offset() {
        return offset;
    }

    public int nextDWord() {
        offset += 4;
        return ByteUtils.readDWord(buf, offset - 4);
    }

    public int nextWord() {
        offset += 2;
        return ByteUtils.readWord(buf, offset - 2);
    }

    public int findNullTermination() {
        return findNullTermination(0);
    }

    public int findNullTermination(int additionalOffset) {
        int i = offset + additionalOffset;
        while (buf[i++] != 0) ;
        return i - 1;
    }

    public String nextNullTerminatedString() {
        String result = ByteUtils.readNullTerminatedString(buf, offset);
        offset += result.getBytes().length + 1;
        return result;
    }

    public String nextNullTerminatedStringAndForward() {
        String result = nextNullTerminatedString();
        forward(1);
        return result;
    }

    public boolean hasMore() {
        return offset < buf.length;
    }

    public void debugWhatIsLeftToFile() {
        ByteUtils.debugToFile(getBuf(), offset(),
                getBuf().length - offset(),
                "decompressed.bin");
    }

    public void swapNextTwo() {
        byte tmp = buf[offset];
        buf[offset] = buf[offset + 1];
        buf[offset + 1] = tmp;
    }

    public <T> List<T> listOfUntil(long limit, Function<ByteReader, T> readFunction) {
        List<T> result = new ArrayList<>();
        while (offset() < limit) {
            result.add(readFunction.apply(this));
        }
        return result;
    }
}
