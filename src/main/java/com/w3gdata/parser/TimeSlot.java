package com.w3gdata.parser;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.w3gdata.util.ByteBuffer;

public class TimeSlot {
    public final int n;
    public final int timeIncrement;
    public final Multimap<Byte, Command> commandDataBlocks;

    public TimeSlot(ByteBuffer buf) {
        n = buf.readWord();
        timeIncrement = buf.readWord();
        commandDataBlocks = ArrayListMultimap.create();
    }

    public int getN() {
        return n;
    }
    public int getTimeIncrement() {
        return timeIncrement;
    }
    public Multimap<Byte, Command> getCommandDataBlocks() {
        return commandDataBlocks;
    }

}
