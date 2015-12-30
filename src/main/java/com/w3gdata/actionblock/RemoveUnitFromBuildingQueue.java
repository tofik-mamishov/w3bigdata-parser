package com.w3gdata.actionblock;

public class RemoveUnitFromBuildingQueue implements ActionBlock {
    public static final byte ID = 0x1E;

    public static final int ADDITIONAL_BYTE = 0x1D;//for WarCraft III patch version <= 1.14b

    public int slotNumber;

    public int itemId;
}
