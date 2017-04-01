package com.w3gdata.parser.action

import com.w3gdata.util.ByteReader

class MinimapSignalAction implements Action {
    public Coordinates coordinates

    MinimapSignalAction(ByteReader reader) {
        coordinates = new Coordinates(reader)
        reader.forward(4)
    }
}
