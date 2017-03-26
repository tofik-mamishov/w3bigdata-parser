package com.w3gdata.actionblock;

public class UnitBuildingAbility implements ActionBlock {
    public static final byte ID = 0x10;

    public int itemId;

    public int abilityFlag;

    public int unknownA;//only present for patch version >= 1.07

    public int unknownB;//only present for patch version >= 1.07
}