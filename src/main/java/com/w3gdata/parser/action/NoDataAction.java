package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

public abstract class NoDataAction implements Action {

    @Override
    public Action deserialize(ByteBuffer buf) {
        return this;
    }
}
