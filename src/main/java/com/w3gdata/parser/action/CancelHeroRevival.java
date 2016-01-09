package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

public class CancelHeroRevival implements Action {
    public static final int ID = 0x1D;

    public static final int ADDITIONAL_BYTE = 0x1C;//for WarCraft III patch version <= 1.14b

    public ObjPair unitPair;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteBuffer buf) {
        CancelHeroRevival cancelHeroRevival = new CancelHeroRevival();
        cancelHeroRevival.unitPair = new ObjPair(buf.readDWord(), buf.readDWord());
        return cancelHeroRevival;
    }
}
