package com.w3gdata.parser.actionblock;

public class EnterChooseHeroSkillSubmenu implements ActionBlock {
    public static final int ID = 0x66;

    public static final int ADDITIONAL_BYTE = 0x65;//for WarCraft III patch version <= 1.06

    @Override
    public int getId() {
        return ID;
    }
}