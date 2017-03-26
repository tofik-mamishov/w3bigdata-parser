package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

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
    public Action deserialize(ByteReader buf) {
        ChangeSelection changeSelection = new ChangeSelection();
        changeSelection.selectMode = buf.nextByte();
        changeSelection.unitsBuildingsNumber = buf.nextWord();
        int limit = buf.offset() + changeSelection.unitsBuildingsNumber * 4 * 2;
        while (buf.offset() < limit) {
            ObjPair pair = new ObjPair();
            pair.objectId1 = buf.nextDWord();
            pair.objectId2 = buf.nextDWord();
            changeSelection.selectedObjs.add(pair);
        }
        return changeSelection;
    }
}
