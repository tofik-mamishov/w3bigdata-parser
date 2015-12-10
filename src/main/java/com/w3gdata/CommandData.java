package com.w3gdata;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.w3gdata.actionblock.ActionBlock;

public class CommandData {
    public byte playerId;

    public int actionBlockLength;

    public Multimap<Byte, ActionBlock> actionBlocks;

    public CommandData() {
        actionBlocks = ArrayListMultimap.create();
    }
}
