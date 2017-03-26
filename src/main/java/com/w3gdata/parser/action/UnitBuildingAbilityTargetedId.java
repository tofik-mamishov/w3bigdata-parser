package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

public class UnitBuildingAbilityTargetedId extends UnitBuildingAbilityTargeted {
    public static final int ID = 0x012;

    public int objId1;

    public int objId2;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteBuffer buf) {
        UnitBuildingAbilityTargetedId ability = new UnitBuildingAbilityTargetedId();
        ability.abilityFlag = buf.readWord();
        ability.itemId = buf.readDWord();
        ability.unknownA = buf.readDWord();
        ability.unknownB = buf.readDWord();
        ability.targetX = buf.readDWord();
        ability.targetY = buf.readDWord();
        ability.objId1 = buf.readDWord();
        ability.objId2 = buf.readDWord();
        return ability;
    }
}
