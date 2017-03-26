package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class UnitBuildingAbility2Targets2Items extends UnitBuildingAbilityTargeted {
    public static final int ID = 0x14;

    public static final int UNKNOWN_BYTES_SIZE = 9;

    public int item2Id;

    public int target2X;

    public int target2Y;

    public byte [] unknownBytes;

    public UnitBuildingAbility2Targets2Items() {
        unknownBytes = new byte[9];
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteReader buf) {
        UnitBuildingAbility2Targets2Items ability = new UnitBuildingAbility2Targets2Items();
        ability.abilityFlag = buf.nextWord();
        ability.itemId = buf.nextDWord();
        ability.unknownA = buf.nextDWord();
        ability.unknownB = buf.nextDWord();
        ability.targetX = buf.nextDWord();
        ability.targetY = buf.nextDWord();
        ability.item2Id = buf.nextDWord();
        ability.unknownBytes = buf.nextBytes(9);
        ability.target2X = buf.nextDWord();
        ability.target2Y = buf.nextDWord();
        return ability;

    }
}
