package com.w3gdata.actionblocks;

import java.util.ArrayList;
import java.util.List;

public class AssignGroupHotkey extends ActionBlock {
    public static final int ID = 0x17;

    public int groupNumber;

    public int selectedObjNumber;

    public List<ObjPair> selectedObjs;

    public AssignGroupHotkey() {
        selectedObjs = new ArrayList<>(selectedObjNumber);
    }
}
