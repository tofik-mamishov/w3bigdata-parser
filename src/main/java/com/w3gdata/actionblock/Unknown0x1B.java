package com.w3gdata.actionblock;

public class Unknown0x1B implements Unknown {
    public static final byte ID = 0x1B;

    public static final int ADDITIONAL_BYTE = 0x1A;//for WarCraft III patch version <= 1.14b

    @Override
    public int getSize() {
        return 10;
    }
}