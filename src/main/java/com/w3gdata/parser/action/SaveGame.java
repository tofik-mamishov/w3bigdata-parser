package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class SaveGame implements Action {
    public static final int ID = 0x06;

    public final String saveGameName;

    public SaveGame(String saveGameName) {
        this.saveGameName = saveGameName;
    }

    public SaveGame() {
        saveGameName = "";
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteReader buf) {
        return new SaveGame(buf.nextNullTerminatedString());
    }
}
