package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class UnitBuildingAbilityTargetedId extends UnitBuildingAbilityTargeted {
    public static final int ID = 0x012;

    public int objId1;

    public int objId2;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteReader buf) {
        UnitBuildingAbilityTargetedId ability = new UnitBuildingAbilityTargetedId();
        ability.abilityFlag = buf.nextWord();
        ability.itemId = buf.nextDWord();
        ability.unknownA = buf.nextDWord();
        ability.unknownB = buf.nextDWord();
        ability.targetX = buf.nextDWord();
        ability.targetY = buf.nextDWord();
        ability.objId1 = buf.nextDWord();
        ability.objId2 = buf.nextDWord();
        return ability;
    }
}
