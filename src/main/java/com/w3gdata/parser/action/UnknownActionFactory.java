package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

import java.util.function.Function;

public enum UnknownActionFactory {
    INSTANCE;

    public static Function<ByteReader, Action> ofSize(final int size) {
        return reader -> new Unknown(reader) {
            @Override
            int getSize() {
                return size;
            }
        };
    }

}
