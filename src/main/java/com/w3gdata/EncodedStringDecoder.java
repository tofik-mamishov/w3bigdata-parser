package com.w3gdata;

public class EncodedStringDecoder {

    public String decode(String encodedString) {
        byte[] decoded = new byte[encodedString.length()];
        byte mask = 0;
        int j = 0;
        for (int i = 0; i < encodedString.length(); i++) {
            byte eCharAtPos = encodedString.getBytes()[i];
            int posR = i % 8;
            if (posR == 0) {
                mask = eCharAtPos;
            } else {
                if ((mask & (0x1 << (posR))) == 0) {
                    decoded[j++] = (byte) (eCharAtPos - 1);
                } else {
                    decoded[j++] = eCharAtPos;
                }
            }
        }
        return new String(decoded);
    }
}
