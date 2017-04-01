package com.w3gdata.parser.action

import com.w3gdata.util.ByteReader


class ItemGivenDroppedAction extends UnitBuildingAbilityTargetedAction {
    public GameObject targetObjId1
    public GameObject targetObjId2
    public GameObject itemObjId1
    public GameObject itemObjId2

    ItemGivenDroppedAction(ByteReader reader) {
        super(reader)
        targetObjId1 = GameObjectRegistry.read(reader)
        targetObjId2 = GameObjectRegistry.read(reader)
        itemObjId1 = GameObjectRegistry.read(reader)
        itemObjId2 = GameObjectRegistry.read(reader)
    }
}
