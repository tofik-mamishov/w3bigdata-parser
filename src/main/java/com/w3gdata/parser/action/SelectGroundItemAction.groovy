package com.w3gdata.parser.action

import com.w3gdata.util.ByteReader


class SelectGroundItemAction implements Action {
    public ObjPair objPair

    SelectGroundItemAction(ByteReader reader) {
        reader.forward(1)
        objPair = new ObjPair(reader)
    }
}
