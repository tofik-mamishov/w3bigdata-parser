package com.w3gdata.parser.action

import com.w3gdata.util.ByteReader


class RemoveUnitFromBuildingQueueAction implements Action {
    public int slotNumber
    public GameObject itemId

    RemoveUnitFromBuildingQueueAction(ByteReader reader) {
        slotNumber = reader.nextByte()
        itemId = GameObjectRegistry.read(reader)
    }
}
