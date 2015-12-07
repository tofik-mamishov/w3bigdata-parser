package com.w3gdata;

import java.util.ArrayList;
import java.util.List;

public class TimeBlock {
    public int n;

    public int timeIncrement;

    public List<CommandData> commandDataBlocks;

    public TimeBlock() {
        commandDataBlocks = new ArrayList<>();
    }
}
