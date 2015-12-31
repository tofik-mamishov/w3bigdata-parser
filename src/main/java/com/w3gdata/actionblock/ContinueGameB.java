package com.w3gdata.actionblock;

public class ContinueGameB implements Unknown {
    public static final int ID = 0x69;

    public static final int ADDITIONAL_BYTE = 0x68;//for WarCraft III patch version <= 1.06

    public static final int SIZE = 17;

    @Override
    public int getSize() {
        return SIZE;
    }
}
