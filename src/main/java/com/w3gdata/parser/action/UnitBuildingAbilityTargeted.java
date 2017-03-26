package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class UnitBuildingAbilityTargeted extends UnitBuildingAbility {
    public static final int ID = 0x11;

    public int targetX;

    public int targetY;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteReader buf) {
        UnitBuildingAbilityTargeted ability = new UnitBuildingAbilityTargeted();
        ability.abilityFlag = buf.nextWord();
        ability.itemId = buf.nextDWord();
        ability.unknownA = buf.nextDWord();
        ability.unknownB = buf.nextDWord();
        ability.targetX = buf.nextDWord();
        ability.targetY = buf.nextDWord();
        return ability;
    }
}
