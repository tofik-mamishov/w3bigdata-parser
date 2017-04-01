package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public abstract class Unknown implements Action {
    abstract int getSize();

    public Unknown(ByteReader reader) {
        reader.forward(getSize());
    }
}
