package com.w3gdata;

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
