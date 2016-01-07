package com.w3gdata.parser.actionblock;

import java.util.HashMap;
import java.util.Map;

public enum CheatFormat {
    THE_DUDE_ABIDES(0x20, 1),
    SOMEBODY_SET_UP_US_THE_BOMB(0x22, 1),
    WARP_TEN(0x23, 1),
    IOCAINE_POWDER(0x24, 1),
    POINT_BREAK(0x25, 1),
    WHOS_YOUR_DADDY(0x26, 1),
    KEYSER_SOZE(0x27, 6),
    LEAFIT_TO_ME(0x28, 6),
    THERE_IS_NO_SPOON(0x29, 1),
    STRENGTH_AND_HONOR(0x2A, 1),
    ITVEXESME(0x2B, 1),
    WHO_IS_JOHN_GALT(0x2C, 1),
    GREED_IS_GOOD(0x2D, 6),
    DAY_LIGHT_SAVINGS(0x2E, 5),
    I_SEE_DEAD_PEOPLE(0x2F, 1),
    SYNERGY(0x30, 1),
    SHARP_AND_SHINY(0x31, 1),
    ALL_YOUR_BASE_ARE_BELONG_TO_US(0x32, 1),
    ;
    final static Map<Integer, CheatFormat> formats;

    int id;

    int size;

    static {
        formats = new HashMap<>();
        for (CheatFormat format : CheatFormat.values()) {
            formats.put(format.id, format);
        }
    }

    CheatFormat(int id, int size) {
        this.id = id;
        this.size = size;
    }

    public static CheatFormat getById(int id) {
        return formats.get(id);
    }
}
