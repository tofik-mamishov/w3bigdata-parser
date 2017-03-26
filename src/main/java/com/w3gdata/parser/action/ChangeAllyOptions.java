package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class ChangeAllyOptions implements Action {
    public static final int ID = 0x50;

    public int playerSlotNumber;

    public int flags;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteReader buf) {
        ChangeAllyOptions changeAllyOptions = new ChangeAllyOptions();
        changeAllyOptions.playerSlotNumber = buf.nextByte();
        changeAllyOptions.flags = buf.nextDWord();
        return changeAllyOptions;
    }
}
