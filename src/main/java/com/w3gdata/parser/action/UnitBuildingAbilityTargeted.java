package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

public class UnitBuildingAbilityTargeted extends UnitBuildingAbility {
    public static final int ID = 0x11;

    public int targetX;

    public int targetY;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteBuffer buf) {
        UnitBuildingAbilityTargeted ability = new UnitBuildingAbilityTargeted();
        ability.abilityFlag = buf.readWord();
        ability.itemId = buf.readDWord();
        ability.unknownA = buf.readDWord();
        ability.unknownB = buf.readDWord();
        ability.targetX = buf.readDWord();
        ability.targetY = buf.readDWord();
        return ability;
    }
}
