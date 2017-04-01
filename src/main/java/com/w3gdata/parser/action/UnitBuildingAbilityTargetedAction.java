package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class UnitBuildingAbilityTargetedAction extends UnitBuildingAbilityAction {
    public int targetX;

    public int targetY;

    public UnitBuildingAbilityTargetedAction(ByteReader reader) {
        super(reader);
        targetX = reader.nextDWord();
        targetY = reader.nextDWord();
    }
}
