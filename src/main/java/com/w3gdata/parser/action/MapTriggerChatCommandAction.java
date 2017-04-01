package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class MapTriggerChatCommandAction implements Action {
    public int unknownA;
    public int unknownB;
    public String chatCommand;

    public MapTriggerChatCommandAction(ByteReader reader) {
        unknownA = reader.nextDWord();
        unknownB = reader.nextDWord();
        chatCommand = reader.nextNullTerminatedString();
    }
}
