package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

public class UnitBuildingAbility implements Action {
    public static final int ID = 0x10;

    public int itemId;

    public int abilityFlag;

    public int unknownA;//only present for patch version >= 1.07

    public int unknownB;//only present for patch version >= 1.07

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteBuffer buf) {
        UnitBuildingAbility ability = new UnitBuildingAbility();
        ability.abilityFlag = buf.readWord();
        ability.itemId = buf.readDWord();
        ability.unknownA = buf.readDWord();
        ability.unknownB = buf.readDWord();
        return ability;
    }
}
