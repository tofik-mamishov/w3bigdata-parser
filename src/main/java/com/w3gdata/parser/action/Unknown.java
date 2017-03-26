package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

public abstract class Unknown implements Action {
    abstract int getSize();

    public Unknown skip(ByteBuffer buf) {
        buf.increment(getSize());
        return this;
    }

    @Override
    public Action deserialize(ByteBuffer buf) {
        return skip(buf);
    }
}
