package com.w3gdata.parser

import jdk.nashorn.internal.ir.annotations.Immutable

@Immutable
class DataBlock {
    public DataBlockHeader header
    public byte[] decompressed

    DataBlock(DataBlockHeader header) {
        this.header = header
        this.decompressed = new byte[header.decompressedSize]
    }

    DataBlockHeader getHeader() {
        return header
    }
}
