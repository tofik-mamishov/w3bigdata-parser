package com.w3gdata.parser.action

import com.w3gdata.util.ByteReader


class CancelHeroRevivalAction implements Action {
    public ObjPair unitPair

    CancelHeroRevivalAction(ByteReader reader) {
        unitPair = new ObjPair(reader)
    }
}
