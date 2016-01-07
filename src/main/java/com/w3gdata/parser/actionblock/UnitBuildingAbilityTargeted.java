package com.w3gdata.parser.actionblock;

public class UnitBuildingAbilityTargeted extends UnitBuildingAbility {
    public static final int ID = 0x11;

    public int targetX;

    public int targetY;

    @Override
    public int getId() {
        return ID;
    }
}
