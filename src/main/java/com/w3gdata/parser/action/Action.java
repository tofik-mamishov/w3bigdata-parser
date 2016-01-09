package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

public interface Action {

    int getId();

    Action deserialize(ByteBuffer buf);
}
