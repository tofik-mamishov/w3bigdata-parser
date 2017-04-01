package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class UnitBuildingAbilityAction implements Action {
    public int itemId;

    public int abilityFlag;

    public int unknownA;//only present for patch version >= 1.07

    public int unknownB;//only present for patch version >= 1.07

    public UnitBuildingAbilityAction(ByteReader reader) {
        abilityFlag = reader.nextWord();
        itemId = reader.nextDWord();
        unknownA = reader.nextDWord();
        unknownB = reader.nextDWord();

    }
}
