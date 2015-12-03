package com.w3gdata;

import java.util.HashMap;
import java.util.Map;

public enum ReplayDataFormat {
    LEAVE_GAME(0x17, 14, true),
    FIRST_STARTBLOCK(0x1A, 5, false),
    SECOND_STARTBLOCK(0x1B, 5, false),
    THIRD_STARTBLOCK(0x1C, 5, false),
    CHECKSUM_RANDOM_OR_SEED(0x22, 6, false),
    UNKNOWN(0x23, 11, false),
    FORCED_GAME_END_COUNTDOWN(0x2F, 9, false);

    static Map<Integer, ReplayDataFormat> formats;

    static {
        formats = new HashMap<>();
        for (ReplayDataFormat format : ReplayDataFormat.values()) {
            formats.put(format.code, format);
        }
    }

    public int code;

    public int size;

    public boolean isKnown;

    ReplayDataFormat(int code, int size, boolean isKnown) {
        this.code = code;
        this.size = size;
        this.isKnown = isKnown;
    }

    public ReplayDataFormat getFormatByCode(int code) {
        return formats.get(code);
    }
}
