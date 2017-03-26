package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

import java.util.ArrayList;
import java.util.List;

public class AssignGroupHotkey implements Action {
    public static final int ID = 0x17;

    public int groupNumber;

    public int selectedObjNumber;

    public List<ObjPair> selectedObjs;

    public AssignGroupHotkey() {
        selectedObjs = new ArrayList<>(selectedObjNumber);
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteReader buf) {
        AssignGroupHotkey assignGroupHotkey = new AssignGroupHotkey();
        assignGroupHotkey.groupNumber = buf.nextByte();
        assignGroupHotkey.selectedObjNumber = buf.nextWord();
        int limit = buf.offset() + assignGroupHotkey.selectedObjNumber * 4 * 2;
        while (buf.offset() < limit) {
            ObjPair pair = new ObjPair();
            pair.objectId1 = buf.nextDWord();
            pair.objectId2 = buf.nextDWord();
            assignGroupHotkey.selectedObjs.add(pair);
        }
        return assignGroupHotkey;
    }
}
