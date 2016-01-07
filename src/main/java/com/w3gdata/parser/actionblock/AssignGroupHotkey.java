package com.w3gdata.parser.actionblock;

import java.util.ArrayList;
import java.util.List;

public class AssignGroupHotkey implements ActionBlock {
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
}
