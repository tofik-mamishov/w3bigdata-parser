package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class CancelHeroRevivalAction implements Action {
    public static final int ADDITIONAL_BYTE = 0x1C;//for WarCraft III patch version <= 1.14b

    public ObjPair unitPair;

    public CancelHeroRevivalAction(ByteReader reader) {
        unitPair = new ObjPair(reader.nextDWord(), reader.nextDWord());
    }
}
