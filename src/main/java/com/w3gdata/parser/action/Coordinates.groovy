package com.w3gdata.parser.action

import com.w3gdata.util.ByteReader


class Coordinates {
    public int x, y

    Coordinates(ByteReader reader) {
        x = reader.nextDWord()
        y = reader.nextDWord()
    }
}
