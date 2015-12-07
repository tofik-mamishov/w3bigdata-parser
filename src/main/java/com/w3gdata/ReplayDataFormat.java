package com.w3gdata;

import java.util.HashMap;
import java.util.Map;

public enum ReplayDataFormat {
    LEAVE_GAME(0x17, 14, true),
    FIRST_STARTBLOCK(0x1A, 5, false),
    SECOND_STARTBLOCK(0x1B, 5, false),
    THIRD_STARTBLOCK(0x1C, 5, false),
    TIME_SLOT_BLOCK_OLD(0x1E, 0, true),
    TIME_SLOT_BLOCK_NEW(0x1F, 0, true),
    PLAYER_CHAT_MESSAGE(0x20, 0, true),
    CHECKSUM_RANDOM_SEED(0x22, 6, false),
    UNKNOWN(0x23, 11, false),
    FORCED_GAME_END_COUNTDOWN(0x2F, 9, true);

    static Map<Integer, ReplayDataFormat> formats;

    static {
        formats = new HashMap<>();
        for (ReplayDataFormat format : ReplayDataFormat.values()) {
            formats.put(format.code, format);
        }
    }

    public int code;

    public int fixedSize;

    public boolean isKnown;

    ReplayDataFormat(int code, int fixedSize, boolean isKnown) {
        this.code = code;
        this.fixedSize = fixedSize;
        this.isKnown = isKnown;
    }

    public static ReplayDataFormat getFormatByCode(int code) {
        return formats.get(code);
    }
}
