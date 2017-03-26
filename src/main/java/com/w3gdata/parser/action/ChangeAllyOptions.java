package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

public class ChangeAllyOptions implements Action {
    public static final int ID = 0x50;

    public int playerSlotNumber;

    public int flags;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteBuffer buf) {
        ChangeAllyOptions changeAllyOptions = new ChangeAllyOptions();
        changeAllyOptions.playerSlotNumber = buf.readByte();
        changeAllyOptions.flags = buf.readDWord();
        return changeAllyOptions;
    }
}
