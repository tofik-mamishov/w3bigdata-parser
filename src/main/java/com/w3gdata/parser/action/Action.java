package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public interface Action {

    int getId();

    Action deserialize(ByteReader buf);
}
