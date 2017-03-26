package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

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
    public Action deserialize(ByteBuffer buf) {
        return new SaveGame(buf.readNullTerminatedString());
    }
}
