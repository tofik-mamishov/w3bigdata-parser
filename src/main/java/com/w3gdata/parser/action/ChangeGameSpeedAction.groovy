package com.w3gdata.parser.action

import com.w3gdata.parser.Speed
import com.w3gdata.util.ByteReader

class ChangeGameSpeedAction implements Action {

    public final Speed speed

    ChangeGameSpeedAction(ByteReader reader) {
        speed = Speed.of(reader.nextByte())
    }
}
