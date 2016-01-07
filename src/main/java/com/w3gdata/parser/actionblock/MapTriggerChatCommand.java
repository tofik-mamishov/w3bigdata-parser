package com.w3gdata.parser.actionblock;

public class MapTriggerChatCommand implements ActionBlock {
    public static final int ID = 0x60;

    public int unknownA;

    public int unknownB;

    public String chatCommand;

    @Override
    public int getId() {
        return ID;
    }
}
