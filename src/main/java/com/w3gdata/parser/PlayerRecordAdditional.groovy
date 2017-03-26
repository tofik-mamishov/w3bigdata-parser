package com.w3gdata.parser

import com.w3gdata.util.ByteReader
import com.w3gdata.util.EnumUtils
import com.w3gdata.util.Valued
import groovy.transform.Canonical


@Canonical
class PlayerRecordAdditional {
    public Types type
    public Races race

    enum Types implements Valued {
        Custom(0x01, 1),
        Ladder(0x16, 4)

        final int value
        final int size

        Types(int value, int size) {
            this.value = value
            this.size = size
        }

        static Types of(int value) {
            EnumUtils.of(Types.class, value)
        }
    }

    enum Races implements Valued {
        Human(0x01),
        Orc(0x02),
        NightElf(0x04),
        Undead(0x08),
        Daemon(0x10),
        Random(0x20),
        SelectableOrFixed(0x40)

        final int value

        Races(int value) {
            this.value = value
        }

        static Races of(int value) {
            EnumUtils.of(Races.class, value)
        }
    }


    PlayerRecordAdditional(ByteReader reader) {
        type = Types.of(reader.nextByteAsInt())
        reader.forward(type.size)
        if (type == Types.Ladder) {
            race = Races.of(reader.nextDWord())
        }
    }
}
