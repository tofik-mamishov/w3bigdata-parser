package com.w3gdata.parser.actionblock;

public class ScenarioTrigger implements ActionBlock {
    public static final int ID = 0x62;

    public int unknownA;

    public int unknownB;

    public int unknownCounter;//only present for patch version >= 1.07

    @Override
    public int getId() {
        return ID;
    }
}
