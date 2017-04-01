package com.w3gdata.parser.action

import com.w3gdata.util.ByteReader


class AssignGroupHotkeyAction implements Action {

    public int groupNumber
    public List<ObjPair> fromToPairs

    AssignGroupHotkeyAction(ByteReader reader) {
        groupNumber = reader.nextByte()
        int selectedObjNumber = reader.nextWord()
        int limit = reader.offset() + selectedObjNumber * 4 * 2
        fromToPairs = reader.listOfUntil(limit, { new ObjPair(it)})
    }
}
