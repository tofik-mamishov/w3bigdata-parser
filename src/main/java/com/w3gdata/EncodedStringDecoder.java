package com.w3gdata;

public class EncodedStringDecoder {

    public String decode(String encodedString) {
        return new String(decode(encodedString.getBytes(), 0, encodedString.length()));
    }

    public byte[] decode(byte[] buf, int offset, int len) {
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
