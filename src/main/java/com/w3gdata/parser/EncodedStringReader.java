package com.w3gdata.parser;

import com.w3gdata.util.ByteReader;

public class EncodedStringReader {

    private final ByteReader reader;

    public EncodedStringReader(ByteReader reader) {
        this.reader = reader;
    }

    public byte[] decode() {
        int currentOffset = reader.offset();
        return decode(reader.getBuf(), currentOffset, reader.findNullTermination() - currentOffset);
    }

    private byte[] decode(byte[] buf, int offset, int len) {
        byte[] decoded = new byte[len];
        byte mask = 0;
        int j = 0;
        for (int i = 0; i < len; i++) {
            byte eCharAtPos = buf[offset + i];
            int posR = i % 8;
            if (posR == 0) {
                mask = eCharAtPos;
            } else {
                if ((mask & (0x1 << posR)) == 0) {
                    decoded[j++] = (byte) (eCharAtPos - 1);
                } else {
                    decoded[j++] = eCharAtPos;
                }
            }
        }
        return decoded;
    }
}
