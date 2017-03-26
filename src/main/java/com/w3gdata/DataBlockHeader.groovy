package com.w3gdata

import com.w3gdata.util.ByteReader
import groovy.transform.Canonical

@Canonical
class DataBlockHeader {
    public static final int SIZE = 0x8

    public int size
    public int decompressedSize

    DataBlockHeader(ByteReader reader) {
        this.size = reader.nextWord()
        this.decompressedSize = reader.nextWord()
    }
}
