package com.w3gdata.parser.action

import com.w3gdata.util.ByteReader
import com.w3gdata.util.EnumUtils
import com.w3gdata.util.Valued
import groovy.transform.TupleConstructor


class ChangeSelectionAction implements Action {

    public Mode mode
    public List<ObjPair> fromToPairs

    @TupleConstructor
    static enum Mode implements Valued {
        AddToSelection(0x01),
        RemoveFromSelection(0x02)

        int value

        static Mode of(int value) {
            EnumUtils.of(Mode.class, value)
        }
    }

    ChangeSelectionAction(ByteReader reader) {
        mode = Mode.of(reader.nextByteAsInt())
        int numberOfUnits = reader.nextWord()
        int limit = reader.offset() + numberOfUnits * 4 * 2
        fromToPairs = reader.listOfUntil(limit, {new ObjPair(it)})
    }
}
