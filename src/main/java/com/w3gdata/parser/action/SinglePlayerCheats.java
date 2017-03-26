package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

//TODO:
public class SinglePlayerCheats implements Action {
    public CheatFormat cheat;

    @Override
    public int getId() {
        return cheat.id;
    }

    @Override
    public Action deserialize(ByteReader buf) {
        return this;
    }
}
