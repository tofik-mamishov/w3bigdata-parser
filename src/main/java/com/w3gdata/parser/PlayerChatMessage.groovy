package com.w3gdata.parser

import com.w3gdata.util.ByteReader
import com.w3gdata.util.EnumUtils
import com.w3gdata.util.Valued
import groovy.transform.TupleConstructor


class PlayerChatMessage {
    byte playerId
    int size
    Type type
    PlayerChatMessageMode mode
    String message

    @TupleConstructor
    static enum Type implements Valued {
        DelayedStartupScreenMessage(0x10),
        Normal(0x20)

        int value

        static Type of(int value) {
            EnumUtils.of(Type.class, value)
        }
    }

    PlayerChatMessage(ByteReader reader) {
        playerId = reader.nextByte()
        size = reader.nextWord()
        type = Type.of(reader.nextByteAsInt())
        mode = new PlayerChatMessageMode(reader)
        message = reader.nextNullTerminatedString()
    }
}
