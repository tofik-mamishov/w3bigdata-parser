package com.w3gdata.actionblock;

public class MinimapSignal implements ActionBlock {
    public static final byte ID = 0x68;

    public static final int ADDITIONAL_BYTE = 0x67;//for WarCraft III patch version <= 1.06

    public int locationX;

    public int locationY;

    public int unknown;
}
