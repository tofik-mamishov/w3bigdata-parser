package com.w3gdata.parser.actionblock;

public class EnterChooseBuildingSubmenu implements ActionBlock {
    public static final int ID = 0x67;

    public static final int ADDITIONAL_BYTE = 0x66;//for WarCraft III patch version <= 1.06

    @Override
    public int getId() {
        return ID;
    }
}
