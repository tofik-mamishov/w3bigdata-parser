package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class UnitBuildingAbility2Targets2ItemsAction extends UnitBuildingAbilityTargetedAction {
    public int item2Id;
    public int target2X;
    public int target2Y;
    public byte [] unknownBytes;

    public UnitBuildingAbility2Targets2ItemsAction(ByteReader reader) {
        super(reader);
        item2Id = reader.nextDWord();
        unknownBytes = reader.nextBytes(9);
        target2X = reader.nextDWord();
        target2Y = reader.nextDWord();
    }
}
