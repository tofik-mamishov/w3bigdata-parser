package com.w3gdata.parser

import com.w3gdata.util.ByteReader
import groovy.transform.Canonical
import groovy.transform.ToString

/*
    offset | size/type | Description
    -------+-----------+-----------------------------------------------------------
    0x0000 | 28 chars  | zero terminated string "Warcraft III recorded game\0x1A\0"
    0x001c |  1 dword  | fileoffset of first compressed data block (header size)
           |           |  0x40 for WarCraft III with patch <= v1.06
           |           |  0x44 for WarCraft III patch >= 1.07 and TFT replays
    0x0020 |  1 dword  | overall size of compressed file
    0x0024 |  1 dword  | replay header version:
           |           |  0x00 for WarCraft III with patch <= 1.06
           |           |  0x01 for WarCraft III patch >= 1.07 and TFT replays
    0x0028 |  1 dword  | overall size of dataReader data (excluding header)
    0x002c |  1 dword  | number of compressed data blocks in file
    0x0030 |  n bytes  | SubHeader (see section 2.1 and 2.2)
     */
@Canonical
@ToString(includePackage = false)
class ReplayHeader {

    static final String LABEL = "Warcraft III recorded game\u001A"

    enum Versions {
        BEFORE_1_06, SINCE_1_07

        static Versions valueOf(int value) {
            values()[value]
        }
    }

    public Versions headerVersion
    public int size
    public int firstDataBlockOffset
    public int decompressedSize
    public int numberOfCompressedDataBlocks

    ReplayHeader(ByteReader reader) {
        String label = reader.nextNullTerminatedString()
        if (LABEL != label) {
            throw new W3gParserException("Invalid file is given, file should start with " + LABEL)
        }
        firstDataBlockOffset = reader.nextDWord()
        size = reader.nextDWord()
        headerVersion = Versions.valueOf(reader.nextDWord())
        decompressedSize = reader.nextDWord()
        numberOfCompressedDataBlocks = reader.nextDWord()
        if (headerVersion != Versions.SINCE_1_07) {
            throw new W3gParserException("Old replays are not supported!")
        }
    }

    ReplayHeader(int headerVersion, int size, int firstDataBlockOffset, int decompressedSize, int numberOfCompressedDataBlocks) {
        this.headerVersion = Versions.valueOf(headerVersion)
        this.size = size
        this.firstDataBlockOffset = firstDataBlockOffset
        this.decompressedSize = decompressedSize
        this.numberOfCompressedDataBlocks = numberOfCompressedDataBlocks
    }

}
