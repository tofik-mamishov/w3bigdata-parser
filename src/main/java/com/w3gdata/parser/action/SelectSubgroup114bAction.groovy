package com.w3gdata.parser.action

import com.w3gdata.util.ByteReader


class SelectSubgroup114bAction implements Action {
    public GameObject unit
    public ObjPair objPair

    SelectSubgroup114bAction(ByteReader reader) {
        unit = GameObjectRegistry.read(reader)
        objPair = new ObjPair(reader)
    }
}
