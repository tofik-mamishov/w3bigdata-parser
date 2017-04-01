package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class SelectGroupHotkeyAction implements Action {
    public int groupNumber;
    public int unknown;

    public SelectGroupHotkeyAction(ByteReader reader) {
        groupNumber = reader.nextByte();
        unknown = reader.nextByte();
    }
}
