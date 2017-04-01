package com.w3gdata.parser.action

import com.w3gdata.util.ByteReader


class UnitBuildingAbilityTargetedAction extends UnitBuildingAbilityAction {
    public Coordinates coordinates

    UnitBuildingAbilityTargetedAction(ByteReader reader) {
        super(reader)
        coordinates = new Coordinates(reader)
    }
}
