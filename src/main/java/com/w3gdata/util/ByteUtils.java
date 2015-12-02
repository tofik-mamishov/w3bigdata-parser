package com.w3gdata.util;

import java.nio.charset.Charset;

public final class ByteUtils {

    private ByteUtils() {

    }

    public static int readDWord(byte[] buf, int offset) {
        return buf[offset + 3] << 24 | (buf[offset + 2] & 0xFF) << 16 | (buf[offset + 1] & 0xFF) << 8 | (buf[offset] & 0xFF);
    }

    public static int readWord(byte[] buf, int offset) {
        return (buf[offset + 1] & 0xFF) << 8 | (buf[offset] & 0xFF);
    }

    public static String readString(byte[] buf, int offset, int len) {
        return new String(buf, offset, len, Charset.defaultCharset());
    }

    public static String readNullTerminatedString(byte[] buf, int offset) {
        int len = findNullTermination(buf, offset) - offset;
        return readString(buf, offset, len);
    }

    public static int findNullTermination(byte[] buf, int offset) {
        int i = offset;
        while(buf[i++] != 0);
        return i - 1;
    }

}
