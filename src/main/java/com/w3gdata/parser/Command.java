package com.w3gdata.parser;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.w3gdata.parser.action.Action;

public class Command {
    public byte playerId;

    public int actionBlockLength;

    public Multimap<Byte, Action> actionBlocks;

    public Command() {
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

    public Multimap<Byte, Action> getActionBlocks() {
        return actionBlocks;
    }

    public void setActionBlocks(Multimap<Byte, Action> actionBlocks) {
        this.actionBlocks = actionBlocks;
    }
}
