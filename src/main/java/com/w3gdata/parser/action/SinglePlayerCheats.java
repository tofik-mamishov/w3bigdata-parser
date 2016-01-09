package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

//TODO:
public class SinglePlayerCheats implements Action {
    public CheatFormat cheat;

    @Override
    public int getId() {
        return cheat.id;
    }

    @Override
    public Action deserialize(ByteBuffer buf) {
        return this;
    }
}
