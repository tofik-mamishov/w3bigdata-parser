package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class SaveGameFinishedAction implements Action {
    public final int unknown;

    public SaveGameFinishedAction(ByteReader reader) {
        unknown = reader.nextDWord();
    }
}
