package com.w3gdata.parser.actionblock;

public class SaveGame implements ActionBlock {
    public static final int ID = 0x06;
    public String saveGameName;//null terminated string

    public SaveGame(String saveGameName) {
        this.saveGameName = saveGameName;
    }

    @Override
    public int getId() {
        return ID;
    }
}
