package com.w3gdata.actionblock;

import java.util.ArrayList;
import java.util.List;

public class AssignGroupHotkey implements ActionBlock {
    public static final byte ID = 0x17;

    public int groupNumber;

    public int selectedObjNumber;

    public List<ObjPair> selectedObjs;

    public AssignGroupHotkey() {
        selectedObjs = new ArrayList<>(selectedObjNumber);
    }
}
