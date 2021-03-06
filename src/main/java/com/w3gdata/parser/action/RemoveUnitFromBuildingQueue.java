package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

public class RemoveUnitFromBuildingQueue implements Action {
    public static final int ID = 0x1E;

    public static final int ADDITIONAL_BYTE = 0x1D;//for WarCraft III patch version <= 1.14b

    public int slotNumber;

    public int itemId;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteBuffer buf) {
        RemoveUnitFromBuildingQueue removeUnitFromBuildingQueue = new RemoveUnitFromBuildingQueue();
        removeUnitFromBuildingQueue.slotNumber = buf.readByte();
        removeUnitFromBuildingQueue.itemId = buf.readDWord();
        return removeUnitFromBuildingQueue;
    }
}
