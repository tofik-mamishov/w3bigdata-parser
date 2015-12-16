package com.w3gdata.actionblock;

import com.w3gdata.util.ByteBuffer;

public interface Unknown extends ActionBlock {
    int getSize();

    default Unknown skip(ByteBuffer buf) {
        buf.increment(getSize());
        return this;
    }
}
