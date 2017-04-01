package com.w3gdata.parser;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.w3gdata.parser.action.Action;
import com.w3gdata.parser.action.Actions;
import com.w3gdata.util.ByteReader;

public class Command {
    public final byte playerId;

    public final int actionBlockLength;

    public final Multimap<Byte, Action> actionBlocks;

    public Command(ByteReader reader) {
        playerId = reader.nextByte();
        actionBlockLength = reader.nextWord();
        actionBlocks = ArrayListMultimap.create();
        actionBlocks.putAll(readActionBlocks(reader));
    }

    private Multimap<Byte, Action> readActionBlocks(ByteReader reader) {
        int limit = actionBlockLength + reader.offset();
        Multimap<Byte, Action> actionBlocks = ArrayListMultimap.create();
        while (reader.offset() < limit) {
            int id = reader.nextByte();
            Action action = Actions.getById(id).shape.deserialize(reader);
            actionBlocks.put((byte) action.getId(), action);
        }
        return actionBlocks;
    }
}
