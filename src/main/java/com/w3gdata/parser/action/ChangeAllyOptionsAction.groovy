package com.w3gdata.parser.action

import com.w3gdata.util.ByteReader

class ChangeAllyOptionsAction implements Action {
    public int playerSlotNumber
    public EnumSet<Flag> flags

    static enum Flag {
        Allied(0x1F),
        VisionShared(0x20),
        UnitControlShared(0x40),
        AlliedVictory(0x0400)

        int mask

        Flag(int mask) {
            this.mask = mask
        }

        static EnumSet<Flag> parse(int value) {
            EnumSet<Flag> result = EnumSet.noneOf(Flag.class)
            for (Flag flag : values()) {
                if (flag.mask & value) {
                    result.add(flag)
                }
            }
            return result
        }
    }

    ChangeAllyOptionsAction(ByteReader reader) {
        playerSlotNumber = reader.nextByte()
        flags = Flag.parse(reader.nextDWord())
    }

    boolean isAllied() {
        flags.contains(Flag.Allied)
    }

    boolean isVisionShared() {
        flags.contains(Flag.VisionShared)
    }

    boolean isUnitControlShared() {
        flags.contains(Flag.UnitControlShared)
    }

    boolean isAlliedVictory() {
        flags.contains(Flag.AlliedVictory)
    }
}
