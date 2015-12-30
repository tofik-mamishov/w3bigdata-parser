package com.w3gdata.actionblock;

public class SaveGame implements ActionBlock {
    public static final byte ID = 0x06;
    public String saveGameName;//null terminated string

    public SaveGame(String saveGameName) {
        this.saveGameName = saveGameName;
    }
}
