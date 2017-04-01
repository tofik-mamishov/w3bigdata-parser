package com.w3gdata.parser.action

import com.w3gdata.util.ByteReader


class SelectGroupHotkeyAction implements Action {
    public int groupNumber

    SelectGroupHotkeyAction(ByteReader reader) {
        groupNumber = reader.nextByte()
        reader.forward(1)
    }
}
