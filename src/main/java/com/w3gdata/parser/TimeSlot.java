package com.w3gdata.parser;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class TimeSlot {
    public int n;

    public int timeIncrement;

    public Multimap<Byte, Command> commandDataBlocks;

    public TimeSlot() {
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

    public Multimap<Byte, Command> getCommandDataBlocks() {
        return commandDataBlocks;
    }

    public void setCommandDataBlocks(Multimap<Byte, Command> commandDataBlocks) {
        this.commandDataBlocks = commandDataBlocks;
    }
}
