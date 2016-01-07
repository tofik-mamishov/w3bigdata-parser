package com.w3gdata.parser.actionblock;

public class SelectGroupHotkey implements ActionBlock {
    public static final int ID = 0x18;

    public int groupNumber;

    public int unknown;

    @Override
    public int getId() {
        return ID;
    }
}
