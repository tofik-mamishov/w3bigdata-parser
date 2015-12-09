package com.w3gdata;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class TimeSlotBlock {
    public int n;

    public int timeIncrement;

    public Multimap<Byte, CommandData> commandDataBlocks;

    public TimeSlotBlock() {
        commandDataBlocks = ArrayListMultimap.create();
    }
}
