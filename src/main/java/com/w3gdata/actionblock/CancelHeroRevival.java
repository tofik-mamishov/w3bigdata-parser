package com.w3gdata.actionblock;

public class CancelHeroRevival implements ActionBlock {
    public static final byte ID = 0x1D;

    public static final int ADDITIONAL_BYTE = 0x1C;//for WarCraft III patch version <= 1.14b

    public ObjPair unitPair;
}
