package com.w3gdata.actionblock;

public class ChangeAllyOptions implements ActionBlock {
    public static final int ID = 0x50;

    public int playerSlotNumber;

    public int flags;

    @Override
    public int getId() {
        return ID;
    }
}
