package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class SelectSubgroup114bAction implements Action {
    public int itemId;
    public ObjPair objPair;

    public SelectSubgroup114bAction(ByteReader reader) {
        itemId = reader.nextDWord();
        objPair = new ObjPair(reader.nextDWord(), reader.nextDWord());
    }
}
