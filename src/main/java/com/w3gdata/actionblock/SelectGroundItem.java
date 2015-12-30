package com.w3gdata.actionblock;

public class SelectGroundItem implements ActionBlock {
    public static final byte ID = 0x1C;

    public static final int ADDITIONAL_BYTE = 0x1B;//for WarCraft III patch version <= 1.14b

    public int unknown;

    public ObjPair objPair;
}
