package com.w3gdata.actionblock;

public class SelectSubgroup implements ActionBlock {
    public static final byte ID = 0x19;

    public int subgroupNumber;

    public SelectSubgroup(int subgroupNumber) {
        this.subgroupNumber = subgroupNumber;
    }
}
