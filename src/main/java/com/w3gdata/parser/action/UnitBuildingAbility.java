package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

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
    public Action deserialize(ByteReader buf) {
        UnitBuildingAbility ability = new UnitBuildingAbility();
        ability.abilityFlag = buf.nextWord();
        ability.itemId = buf.nextDWord();
        ability.unknownA = buf.nextDWord();
        ability.unknownB = buf.nextDWord();
        return ability;
    }
}
