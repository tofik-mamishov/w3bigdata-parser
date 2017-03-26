package com.w3gdata

import jdk.nashorn.internal.ir.annotations.Immutable

@Immutable
class DataBlock {
    public DataBlockHeader header
    public byte[] decompressed

    DataBlock(DataBlockHeader header, byte[] decompressed) {
        this.header = header
        this.decompressed = decompressed
    }
}
