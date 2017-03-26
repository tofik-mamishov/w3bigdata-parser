package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

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
    public Action deserialize(ByteBuffer buf) {
        AssignGroupHotkey assignGroupHotkey = new AssignGroupHotkey();
        assignGroupHotkey.groupNumber = buf.readByte();
        assignGroupHotkey.selectedObjNumber = buf.readWord();
        int limit = buf.getOffset() + assignGroupHotkey.selectedObjNumber * 4 * 2;
        while (buf.getOffset() < limit) {
            ObjPair pair = new ObjPair();
            pair.objectId1 = buf.readDWord();
            pair.objectId2 = buf.readDWord();
            assignGroupHotkey.selectedObjs.add(pair);
        }
        return assignGroupHotkey;
    }
}
