package com.w3gdata.parser.action

import com.w3gdata.util.ByteReader


class UnitBuildingAbilityTargetedIdAction extends UnitBuildingAbilityTargetedAction {
    public GameObject objId1
    public GameObject objId2

    UnitBuildingAbilityTargetedIdAction(ByteReader reader) {
        super(reader)
        objId1 = GameObjectRegistry.read(reader)
        objId2 = GameObjectRegistry.read(reader)
    }
}
