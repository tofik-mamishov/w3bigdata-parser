package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

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
    public Action deserialize(ByteBuffer buf) {
        ItemGivenDropped ability = new ItemGivenDropped();
        ability.abilityFlag = buf.readWord();
        ability.itemId = buf.readDWord();
        ability.unknownA = buf.readDWord();
        ability.unknownB = buf.readDWord();
        ability.targetX = buf.readDWord();
        ability.targetY = buf.readDWord();
        ability.targetObjId1 = buf.readDWord();
        ability.targetObjId2 = buf.readDWord();
        ability.itemObjId1 = buf.readDWord();
        ability.itemObjId2 = buf.readDWord();
        return ability;
    }
}
