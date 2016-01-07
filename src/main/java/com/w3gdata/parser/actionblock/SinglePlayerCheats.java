package com.w3gdata.parser.actionblock;

public class SinglePlayerCheats implements ActionBlock {
    public CheatFormat cheat;

    @Override
    public int getId() {
        return cheat.id;
    }
}
