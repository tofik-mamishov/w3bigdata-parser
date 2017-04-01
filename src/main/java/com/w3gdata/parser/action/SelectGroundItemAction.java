package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class SelectGroundItemAction implements Action {
    public static final int ADDITIONAL_BYTE = 0x1B;//for WarCraft III patch version <= 1.14b

    public int unknown;

    public ObjPair objPair;

    public SelectGroundItemAction(ByteReader reader) {
        unknown = reader.nextByte();
        objPair = new ObjPair(reader.nextDWord(), reader.nextDWord());
    }
}
