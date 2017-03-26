package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class MinimapSignal implements Action {
    public static final int ID = 0x68;

    public static final int ADDITIONAL_BYTE = 0x67;//for WarCraft III patch version <= 1.06

    public int locationX;

    public int locationY;

    public int unknown;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteReader buf) {
        MinimapSignal minimapSignal = new MinimapSignal();
        minimapSignal.locationX = buf.nextDWord();
        minimapSignal.locationY = buf.nextDWord();
        minimapSignal.unknown = buf.nextDWord();
        return minimapSignal;
    }
}
