package com.w3gdata.parser

import com.w3gdata.util.ByteReader
import com.w3gdata.util.ByteUtils
import groovy.transform.Canonical

@Canonical
class DataBlockHeader {
    public static final int SIZE = 0x8

    public int size
    public int decompressedSize
    public int checksum

    DataBlockHeader(ByteReader reader) {
        size = reader.nextWord()
        decompressedSize = reader.nextWord()
        checksum = reader.nextDWord()
    }

    int getSize() {
        return size
    }
}
