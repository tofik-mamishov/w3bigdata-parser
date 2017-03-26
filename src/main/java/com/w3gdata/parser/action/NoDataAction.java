package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public abstract class NoDataAction implements Action {

    @Override
    public Action deserialize(ByteReader buf) {
        return this;
    }
}
