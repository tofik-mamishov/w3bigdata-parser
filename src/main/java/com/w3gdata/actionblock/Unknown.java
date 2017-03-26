package com.w3gdata.actionblock;

import com.w3gdata.util.ByteReader;

public interface Unknown extends ActionBlock {
    int getSize();

    default Unknown skip(ByteReader buf) {
        buf.forward(getSize());
        return this;
    }
}
