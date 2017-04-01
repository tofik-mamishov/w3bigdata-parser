package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class SaveGameAction implements Action {
    public final String saveGameName;

    public SaveGameAction(ByteReader reader) {
        saveGameName = reader.nextNullTerminatedString();
    }
}
