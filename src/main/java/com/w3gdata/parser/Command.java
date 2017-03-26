package com.w3gdata.parser;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.w3gdata.parser.action.Action;
import com.w3gdata.util.ByteReader;

public class Command {
    public final byte playerId;

    public final int actionBlockLength;

    public final Multimap<Byte, Action> actionBlocks;

    public Command(ByteReader buf) {
        playerId = buf.nextByte();
        actionBlockLength = buf.nextWord();
        actionBlocks = ArrayListMultimap.create();
    }

    public byte getPlayerId() {
        return playerId;
    }
    public int getActionBlockLength() {
        return actionBlockLength;
    }
    public Multimap<Byte, Action> getActionBlocks() {
        return actionBlocks;
    }
}
