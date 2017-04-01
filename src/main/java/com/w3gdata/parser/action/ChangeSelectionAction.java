package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

import java.util.ArrayList;
import java.util.List;

public class ChangeSelectionAction implements Action {
    public static final int ID = 0x16;

    public static final int ADD_TO_SELECTION = 0x01;
    public static final int REMOVE_FROM_SELECTION = 0x02;

    public int selectMode;

    public int unitsBuildingsNumber;

    public List<ObjPair> selectedObjs;

    public ChangeSelectionAction(ByteReader reader) {
        selectMode = reader.nextByte();
        unitsBuildingsNumber = reader.nextWord();
        int limit = reader.offset() + unitsBuildingsNumber * 4 * 2;
        while (reader.offset() < limit) {
            ObjPair pair = new ObjPair();
            pair.objectId1 = reader.nextDWord();
            pair.objectId2 = reader.nextDWord();
            selectedObjs.add(pair);
        }
    }
}
