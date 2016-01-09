package com.w3gdata.parser.action;

public class EnterChooseBuildingSubmenu extends NoDataAction {
    public static final int ID = 0x67;

    public static final int ADDITIONAL_BYTE = 0x66;//for WarCraft III patch version <= 1.06

    @Override
    public int getId() {
        return ID;
    }
}
