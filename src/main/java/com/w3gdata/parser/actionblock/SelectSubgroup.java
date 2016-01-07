package com.w3gdata.parser.actionblock;

public class SelectSubgroup implements ActionBlock {
    public static final int ID = 0x19;

    public int subgroupNumber;

    public SelectSubgroup(int subgroupNumber) {
        this.subgroupNumber = subgroupNumber;
    }

    @Override
    public int getId() {
        return ID;
    }
}
