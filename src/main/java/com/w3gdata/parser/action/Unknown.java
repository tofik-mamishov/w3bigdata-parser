package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public abstract class Unknown implements Action {
    abstract int getSize();

    public Unknown skip(ByteReader buf) {
        buf.forward(getSize());
        return this;
    }

    @Override
    public Action deserialize(ByteReader buf) {
        return skip(buf);
    }
}
