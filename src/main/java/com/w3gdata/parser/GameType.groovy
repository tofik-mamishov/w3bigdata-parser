package com.w3gdata.parser

import com.w3gdata.util.ByteReader
import com.w3gdata.util.EnumUtils
import com.w3gdata.util.Valued
import groovy.transform.Canonical
import groovy.transform.TupleConstructor


@Canonical
class GameType {

    public Type type
    public Privacy privacy
    public int unknown

    @TupleConstructor
    static enum Type implements Valued {
        Unknown(0x00),
        LadderOrCustomScenario(0x01),
        CustomGame(0x09),
        SinglePlayerGame(0x1D),
        LadderTeamGameATOrRT(0x20)

        int value

        static Type of(int value) {
            EnumUtils.of(Type.class, value)
        }
    }

    @TupleConstructor
    static enum Privacy implements Valued {
        Public(0x00), Private(0x08)

        int value

        static Privacy of(int value) {
            EnumUtils.of(Privacy.class, value)
        }
    }

    GameType(ByteReader reader) {
        type = Type.of(reader.nextByteAsInt())
        privacy = Privacy.of(reader.nextByteAsInt())
        unknown = reader.nextWord()
    }
}
