package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

public class SelectSubgroup114b implements Action {
    public static final int ID = 0x19;

    public int itemId;

    public ObjPair objPair;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteBuffer buf) {
        SelectSubgroup114b selectSubgroup114b = new SelectSubgroup114b();
        selectSubgroup114b.itemId = buf.readDWord();
        selectSubgroup114b.objPair = new ObjPair(buf.readDWord(), buf.readDWord());
        return selectSubgroup114b;
    }
}
