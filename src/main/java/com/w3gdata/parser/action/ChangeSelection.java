package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

import java.util.ArrayList;
import java.util.List;

public class ChangeSelection implements Action {
    public static final int ID = 0x16;

    public static final int ADD_TO_SELECTION = 0x01;

    public static final int REMOVE_FROM_SELECTION = 0x02;

    public int selectMode;

    public int unitsBuildingsNumber;

    public List<ObjPair> selectedObjs;

    public ChangeSelection() {
        selectedObjs = new ArrayList<>(unitsBuildingsNumber);
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteBuffer buf) {
        ChangeSelection changeSelection = new ChangeSelection();
        changeSelection.selectMode = buf.readByte();
        changeSelection.unitsBuildingsNumber = buf.readWord();
        int limit = buf.getOffset() + changeSelection.unitsBuildingsNumber * 4 * 2;
        while (buf.getOffset() < limit) {
            ObjPair pair = new ObjPair();
            pair.objectId1 = buf.readDWord();
            pair.objectId2 = buf.readDWord();
            changeSelection.selectedObjs.add(pair);
        }
        return changeSelection;
    }
}
