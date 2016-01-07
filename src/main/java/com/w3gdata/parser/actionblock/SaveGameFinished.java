package com.w3gdata.parser.actionblock;

public class SaveGameFinished implements ActionBlock {
    public static final int ID = 0x07;

    public int unknown;//always 0x00000001 so far

    public SaveGameFinished(int unknown) {
        this.unknown = unknown;
    }

    @Override
    public int getId() {
        return ID;
    }
}
