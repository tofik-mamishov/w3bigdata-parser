package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class SelectSubgroup implements Action {
    public static final int ID = 0x19;

    public final int subgroupNumber;

    public SelectSubgroup(int subgroupNumber) {
        this.subgroupNumber = subgroupNumber;
    }

    public SelectSubgroup() {
        subgroupNumber = 0;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteReader buf) {
        return new SelectSubgroup(buf.nextByte());
    }
}
