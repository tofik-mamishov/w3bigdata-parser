package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class ItemGivenDropped extends UnitBuildingAbilityTargeted {
    public static final int ID = 0x13;

    public int targetObjId1;

    public int targetObjId2;

    public int itemObjId1;

    public int itemObjId2;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteReader buf) {
        ItemGivenDropped ability = new ItemGivenDropped();
        ability.abilityFlag = buf.nextWord();
        ability.itemId = buf.nextDWord();
        ability.unknownA = buf.nextDWord();
        ability.unknownB = buf.nextDWord();
        ability.targetX = buf.nextDWord();
        ability.targetY = buf.nextDWord();
        ability.targetObjId1 = buf.nextDWord();
        ability.targetObjId2 = buf.nextDWord();
        ability.itemObjId1 = buf.nextDWord();
        ability.itemObjId2 = buf.nextDWord();
        return ability;
    }
}
