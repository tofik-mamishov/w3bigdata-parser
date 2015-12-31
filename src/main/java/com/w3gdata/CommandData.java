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

    public byte getPlayerId() {
        return playerId;
    }

    public void setPlayerId(byte playerId) {
        this.playerId = playerId;
    }

    public int getActionBlockLength() {
        return actionBlockLength;
    }

    public void setActionBlockLength(int actionBlockLength) {
        this.actionBlockLength = actionBlockLength;
    }

    public Multimap<Byte, ActionBlock> getActionBlocks() {
        return actionBlocks;
    }

    public void setActionBlocks(Multimap<Byte, ActionBlock> actionBlocks) {
        this.actionBlocks = actionBlocks;
    }
}
