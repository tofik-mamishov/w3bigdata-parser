package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class RemoveUnitFromBuildingQueueAction implements Action {
    public static final int ADDITIONAL_BYTE = 0x1D;//for WarCraft III patch version <= 1.14b

    public int slotNumber;

    public int itemId;

    public RemoveUnitFromBuildingQueueAction(ByteReader reader) {
        slotNumber = reader.nextByte();
        itemId = reader.nextDWord();
    }
}
