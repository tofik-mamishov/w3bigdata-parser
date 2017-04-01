package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

import java.util.ArrayList;
import java.util.List;

public class AssignGroupHotkeyAction implements Action {

    public int groupNumber;
    public int selectedObjNumber;
    public List<ObjPair> selectedObjs;

    public AssignGroupHotkeyAction(ByteReader reader) {
        groupNumber = reader.nextByte();
        selectedObjNumber = reader.nextWord();
        int limit = reader.offset() + selectedObjNumber * 4 * 2;
        while (reader.offset() < limit) {
            ObjPair pair = new ObjPair();
            pair.objectId1 = reader.nextDWord();
            pair.objectId2 = reader.nextDWord();
            selectedObjs.add(pair);
        }
    }
}
