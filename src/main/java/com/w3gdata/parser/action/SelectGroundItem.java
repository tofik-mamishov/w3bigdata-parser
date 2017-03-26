package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class SelectGroundItem implements Action {
    public static final int ID = 0x1C;

    public static final int ADDITIONAL_BYTE = 0x1B;//for WarCraft III patch version <= 1.14b

    public int unknown;

    public ObjPair objPair;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteReader buf) {
        SelectGroundItem selectGroundItem = new SelectGroundItem();
        selectGroundItem.unknown = buf.nextByte();
        selectGroundItem.objPair = new ObjPair(buf.nextDWord(), buf.nextDWord());
        return selectGroundItem;
    }
}
