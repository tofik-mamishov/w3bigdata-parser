package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

public class SaveGameFinished implements Action {
    public static final int ID = 0x07;

    public final int unknown;

    public SaveGameFinished(int unknown) {
        this.unknown = unknown;
    }

    public SaveGameFinished() {
        unknown = 0;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteBuffer buf) {
        return new SaveGameFinished(buf.readDWord());
    }
}
