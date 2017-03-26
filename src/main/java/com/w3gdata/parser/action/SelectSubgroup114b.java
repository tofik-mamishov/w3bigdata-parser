package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class SelectSubgroup114b implements Action {
    public static final int ID = 0x19;

    public int itemId;

    public ObjPair objPair;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteReader buf) {
        SelectSubgroup114b selectSubgroup114b = new SelectSubgroup114b();
        selectSubgroup114b.itemId = buf.nextDWord();
        selectSubgroup114b.objPair = new ObjPair(buf.nextDWord(), buf.nextDWord());
        return selectSubgroup114b;
    }
}
