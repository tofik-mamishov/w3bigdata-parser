package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class MinimapSignalAction implements Action {
    public static final int ADDITIONAL_BYTE = 0x67;//for WarCraft III patch version <= 1.06

    public int locationX;

    public int locationY;

    public int unknown;

    public MinimapSignalAction(ByteReader reader) {
        locationX = reader.nextDWord();
        locationY = reader.nextDWord();
        unknown = reader.nextDWord();
    }
}
