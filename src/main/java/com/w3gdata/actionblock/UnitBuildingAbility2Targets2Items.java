package com.w3gdata.actionblock;

public class UnitBuildingAbility2Targets2Items extends UnitBuildingAbilityTargeted {
    public static final int ID = 0x14;

    public static final int UNKNOWN_BYTES_SIZE = 9;

    public int item2Id;

    public int target2X;

    public int target2Y;

    public byte [] unknownBytes;

    public UnitBuildingAbility2Targets2Items() {
        unknownBytes = new byte[9];
    }
}
