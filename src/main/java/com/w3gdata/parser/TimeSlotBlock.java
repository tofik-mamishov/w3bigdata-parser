package com.w3gdata.parser;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class TimeSlotBlock {
    public int n;

    public int timeIncrement;

    public Multimap<Byte, CommandData> commandDataBlocks;

    public TimeSlotBlock() {
        commandDataBlocks = ArrayListMultimap.create();
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getTimeIncrement() {
        return timeIncrement;
    }

    public void setTimeIncrement(int timeIncrement) {
        this.timeIncrement = timeIncrement;
    }

    public Multimap<Byte, CommandData> getCommandDataBlocks() {
        return commandDataBlocks;
    }

    public void setCommandDataBlocks(Multimap<Byte, CommandData> commandDataBlocks) {
        this.commandDataBlocks = commandDataBlocks;
    }
}
