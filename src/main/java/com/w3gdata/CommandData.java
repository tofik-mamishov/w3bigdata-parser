package com.w3gdata;

import com.w3gdata.actionblocks.ActionBlock;

import java.util.ArrayList;
import java.util.List;

public class CommandData {
    public byte playerId;

    public int actionBlockLength;

    public List<ActionBlock> actionBlocks;

    public CommandData() {
        actionBlocks = new ArrayList<>();
    }
}
