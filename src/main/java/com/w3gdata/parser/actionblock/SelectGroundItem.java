package com.w3gdata.parser.actionblock;

public class SelectGroundItem implements ActionBlock {
    public static final int ID = 0x1C;

    public static final int ADDITIONAL_BYTE = 0x1B;//for WarCraft III patch version <= 1.14b

    public int unknown;

    public ObjPair objPair;

    @Override
    public int getId() {
        return ID;
    }
}