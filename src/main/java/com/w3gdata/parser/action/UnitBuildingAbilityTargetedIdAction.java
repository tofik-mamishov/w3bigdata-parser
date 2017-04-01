package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class UnitBuildingAbilityTargetedIdAction extends UnitBuildingAbilityTargetedAction {
    public int objId1;

    public int objId2;

    public UnitBuildingAbilityTargetedIdAction(ByteReader reader) {
        super(reader);
        objId1 = reader.nextDWord();
        objId2 = reader.nextDWord();
    }
}
