package com.w3gdata.parser.action

import com.w3gdata.util.ByteReader


class MapTriggerChatCommandAction implements Action {
    public String chatCommand

    MapTriggerChatCommandAction(ByteReader reader) {
        reader.forward(8)
        chatCommand = reader.nextNullTerminatedString()
    }
}
