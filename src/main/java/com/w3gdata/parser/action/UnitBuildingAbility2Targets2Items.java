package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

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
    public Action deserialize(ByteBuffer buf) {
        UnitBuildingAbility2Targets2Items ability = new UnitBuildingAbility2Targets2Items();
        ability.abilityFlag = buf.readWord();
        ability.itemId = buf.readDWord();
        ability.unknownA = buf.readDWord();
        ability.unknownB = buf.readDWord();
        ability.targetX = buf.readDWord();
        ability.targetY = buf.readDWord();
        ability.item2Id = buf.readDWord();
        ability.unknownBytes = buf.readBytes(9);
        ability.target2X = buf.readDWord();
        ability.target2Y = buf.readDWord();
        return ability;

    }
}
