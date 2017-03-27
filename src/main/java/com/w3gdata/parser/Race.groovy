package com.w3gdata.parser

import com.w3gdata.util.ByteReader
import com.w3gdata.util.EnumUtils
import com.w3gdata.util.Valued
import groovy.transform.TupleConstructor


class Race {

    public Faction faction
    public Selectability selectability

    @TupleConstructor
    static enum Faction implements Valued {
        Human(0x01),
        Orc(0x02),
        NightElf(0x04),
        Undead(0x08),
        Daemon(0x10),
        Random(0x20)

        int value

        static Faction of(int value) {
            EnumUtils.of(Faction.class, value ^ 0x40)
        }
    }

    static enum Selectability {
        Selectable, Fixed

        static Selectability of(int value) {
            isSelectable(value) ? Selectable : Fixed
        }

        static boolean isSelectable(int value) {
            value & 0x40
        }
    }

    Race(int race) {
        faction = Faction.of(race)
        selectability = Selectability.of(race)
    }
}
