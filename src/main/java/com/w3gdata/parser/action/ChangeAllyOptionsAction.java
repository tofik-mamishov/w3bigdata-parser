package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class ChangeAllyOptionsAction implements Action {
    public int playerSlotNumber;
    public int flags;

    public ChangeAllyOptionsAction(ByteReader reader) {
        playerSlotNumber = reader.nextByte();
        flags = reader.nextDWord();
    }
}
