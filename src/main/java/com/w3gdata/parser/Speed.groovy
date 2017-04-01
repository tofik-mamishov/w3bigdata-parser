package com.w3gdata.parser

import com.w3gdata.util.EnumUtils
import com.w3gdata.util.Valued
import groovy.transform.TupleConstructor

@TupleConstructor
enum Speed implements Valued {
    Slow(0), Normal(1), Fast(2)

    int value

    static Speed of(int value) {
        EnumUtils.of(Speed.class, value)
    }
}