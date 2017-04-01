package com.w3gdata.parser.action

import com.w3gdata.util.ByteReader


class UnitBuildingAbility2Targets2ItemsAction extends UnitBuildingAbilityTargetedAction {
    public GameObject item2
    public Coordinates secondTargetCoordinates
    public byte [] unknownBytes

    UnitBuildingAbility2Targets2ItemsAction(ByteReader reader) {
        super(reader)
        item2 = GameObjectRegistry.read(reader)
        unknownBytes = reader.nextBytes(9)
        secondTargetCoordinates = new Coordinates(reader)
    }
}
