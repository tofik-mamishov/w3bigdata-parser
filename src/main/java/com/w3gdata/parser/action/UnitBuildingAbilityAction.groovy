package com.w3gdata.parser.action

import com.w3gdata.util.ByteReader

class UnitBuildingAbilityAction implements Action {
    public GameObject ability
    public AbilityFlags flags

    UnitBuildingAbilityAction(ByteReader reader) {
        flags = new AbilityFlags(reader)
        ability = GameObjectRegistry.read(reader)
        reader.forward(8)
    }
}
