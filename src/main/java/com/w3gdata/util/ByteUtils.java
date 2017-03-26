package com.w3gdata.util;

import com.w3gdata.parser.Offset;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public final class ByteUtils {

    private ByteUtils() {

    }

    public static int readDWord(byte[] buf, int offset) {
        return buf[offset + 3] << 24 | (buf[offset + 2] & 0xFF) << 16 | (buf[offset + 1] & 0xFF) << 8 | (buf[offset] & 0xFF);
    }

    public static int readDWord(byte[] buf, Offset offset) {
        return readDWord(buf, offset.value());
    }

    public static int readWord(byte[] buf, int offset) {
        return (buf[offset + 1] & 0xFF) << 8 | (buf[offset] & 0xFF);
    }

    public static int readWord(byte[] buf, Offset offset) {
        return readWord(buf, offset);
    }

    public static void writeWord(byte[] buf, int offset, int value) {
        buf[offset + 1] = (byte) ((value >> 8) & 0xFF);
        buf[offset] = (byte) (value & 0xFF);
    }

    public static void writeDWord(byte[] buf, int offset, int value) {
        buf[offset + 3] = (byte) ((value >> 24) & 0xFF);
        buf[offset + 2] = (byte) ((value >> 16) & 0xFF);
        buf[offset + 1] = (byte) ((value >> 8) & 0xFF);
        buf[offset] = (byte) (value & 0xFF);
    }

    public static void writeBytes(byte[] where, int offset, byte[] what) {
        System.arraycopy(what, 0, where, offset, what.length);
    }

    public static String readString(byte[] buf, int offset, int len) {
        return new String(buf, offset, len, Charset.defaultCharset());
    }

    public static String readString(byte[] buf, Offset offset, int len) {
        return readString(buf, offset.value(), len);
    }

    public static String readNullTerminatedString(byte[] buf, int offset) {
        int len = findNullTermination(buf, offset) - offset;
        return readString(buf, offset, len);
    }

    public static String readNullTerminatedString(byte[] buf, Offset offset) {
        return readNullTerminatedString(buf, offset.value());
    }

    public static int findNullTermination(byte[] buf, int offset) {
        int i = offset;
        while(buf[i++] != 0);
        return i - 1;
    }

    public static int findNullTermination(byte[] buf, Offset offset) {
        return findNullTermination(buf, offset.value());
    }

    public static void debugToFile(byte[] decompressed, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(decompressed);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void debugToFile(byte[] decompressed, int offset, int len, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(decompressed, offset, len);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void debugToFile(byte[] decompressed, Offset offset, int len, String fileName) {
        debugToFile(decompressed, offset.value(), len, fileName);
    }

    public static byte[] readBytes(byte[] buf, int offset, int n) {
        byte[] result = new byte[n];
        for (int i = 0; i < n; i++) {
            result[i] = buf[offset++];
        }
        return result;
    }

    public static byte[] readBytes(byte[] buf, Offset offset, int n) {
        return readBytes(buf, offset.value(), n);
    }

}
