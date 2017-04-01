package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class ItemGivenDroppedAction extends UnitBuildingAbilityTargetedAction {
    public int targetObjId1;
    public int targetObjId2;
    public int itemObjId1;
    public int itemObjId2;

    public ItemGivenDroppedAction(ByteReader reader) {
        super(reader);
        targetObjId1 = reader.nextDWord();
        targetObjId2 = reader.nextDWord();
        itemObjId1 = reader.nextDWord();
        itemObjId2 = reader.nextDWord();
    }
}
