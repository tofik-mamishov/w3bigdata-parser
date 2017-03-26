package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

public class SelectGroupHotkey implements Action {
    public static final int ID = 0x18;

    public int groupNumber;

    public int unknown;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteBuffer buf) {
        SelectGroupHotkey selectGroupHotkey = new SelectGroupHotkey();
        selectGroupHotkey.groupNumber = buf.readByte();
        selectGroupHotkey.unknown = buf.readByte();
        return selectGroupHotkey;
    }
}
