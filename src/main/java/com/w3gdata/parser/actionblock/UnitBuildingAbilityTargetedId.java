package com.w3gdata.parser.actionblock;

public class UnitBuildingAbilityTargetedId extends UnitBuildingAbilityTargeted {
    public static final int ID = 0x012;

    public int objId1;

    public int objId2;

    @Override
    public int getId() {
        return ID;
    }
}
