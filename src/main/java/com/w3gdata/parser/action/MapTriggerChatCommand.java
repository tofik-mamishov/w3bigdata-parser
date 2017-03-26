package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

public class MapTriggerChatCommand implements Action {
    public static final int ID = 0x60;

    public int unknownA;

    public int unknownB;

    public String chatCommand;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteBuffer buf) {
        MapTriggerChatCommand mapTriggerChatCommand = new MapTriggerChatCommand();
        mapTriggerChatCommand.unknownA = buf.readDWord();
        mapTriggerChatCommand.unknownB = buf.readDWord();
        mapTriggerChatCommand.chatCommand = buf.readNullTerminatedString();
        return mapTriggerChatCommand;
    }
}
