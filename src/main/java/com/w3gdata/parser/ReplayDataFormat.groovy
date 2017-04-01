package com.w3gdata.parser

import com.w3gdata.util.EnumUtils
import com.w3gdata.util.Valued
import groovy.transform.TupleConstructor

@TupleConstructor
enum ReplayDataFormat implements Valued {
    LEAVE_GAME(0x17, 14, true),
    FIRST_STARTBLOCK(0x1A, 5, false),
    SECOND_STARTBLOCK(0x1B, 5, false),
    THIRD_STARTBLOCK(0x1C, 5, false),
    TIME_SLOT_BLOCK_OLD(0x1E, 0, true),
    TIME_SLOT_BLOCK_NEW(0x1F, 0, true),
    PLAYER_CHAT_MESSAGE(0x20, 0, true),
    CHECKSUM_RANDOM_SEED(0x22, 6, false),
    UNKNOWN(0x23, 11, false),
    FORCED_GAME_END_COUNTDOWN(0x2F, 9, true)

    int value
    public int fixedSize
    public boolean isKnown

    ReplayDataFormat(int value, int fixedSize, boolean isKnown) {
        this.value = value
        this.fixedSize = fixedSize
        this.isKnown = isKnown
    }

    static Optional<ReplayDataFormat> of(int value) {
        for (ReplayDataFormat format : values()) {
            if (format.value == value) {
                return Optional.of(format)
            }
        }
        return Optional.empty()
    }
}