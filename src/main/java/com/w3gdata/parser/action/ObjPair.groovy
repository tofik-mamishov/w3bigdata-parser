package com.w3gdata.parser.action

import com.w3gdata.util.ByteReader

class ObjPair {
    public GameObject from, to

    ObjPair(ByteReader reader) {
        from = GameObjectRegistry.read(reader)
        to = GameObjectRegistry.read(reader)
    }
}
