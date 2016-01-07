package com.w3gdata.parser.actionblock;

public class Unknown0x75 implements Unknown {
    public static byte ID = 0x75;

    public int unknown;

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public int getId() {
        return ID;
    }
}
