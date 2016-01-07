package com.w3gdata.parser.actionblock;

public class ContinueGameB implements Unknown {
    public static final int ID = 0x69;

    public static final int ADDITIONAL_BYTE = 0x68;//for WarCraft III patch version <= 1.06

    public static final int SIZE = 16;

    @Override
    public int getSize() {
        return SIZE;
    }

    @Override
    public int getId() {
        return ID;
    }
}
