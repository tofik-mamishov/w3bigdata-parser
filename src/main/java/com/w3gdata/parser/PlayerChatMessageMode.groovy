package com.w3gdata.parser

import com.w3gdata.util.ByteReader
import com.w3gdata.util.EnumUtils
import com.w3gdata.util.Valued
import groovy.transform.TupleConstructor


class PlayerChatMessageMode {

    Type type
    int playerSlotNumber

    @TupleConstructor
    static enum Type implements Valued {
        AllPlayers(0x00),
        Allies(0x01),
        ObserversOrReferees(0x02),
        SpecificPlayer
        int value

        static Type of(int value) {
            if (value >= 0x03) {
                return SpecificPlayer
            }
            EnumUtils.of(Type.class, value)
        }
    }

    PlayerChatMessageMode(ByteReader reader) {
        int type = reader.nextDWord()
        this.type = Type.of(type)
        if (this.type == Type.SpecificPlayer) {
            playerSlotNumber = type - 0x03
        } else {
            playerSlotNumber = -1
        }
    }
}
