package com.w3gdata.parser

import com.w3gdata.util.ByteReader
import com.w3gdata.util.EnumUtils
import com.w3gdata.util.Valued
import groovy.transform.Canonical
import groovy.transform.TupleConstructor


@Canonical
class GameStartRecord {
    private static final byte RECORD_ID = 0x19
    
    public int totalSize
    public Mode mode
    public byte numberOfPossibleSpawns
    public List<SlotRecord> slots = new ArrayList<>()
    public int randomSeed

    @TupleConstructor
    static enum Mode implements Valued {
        TeamAndRaceSelectable(0x00),
        TeamNotSelectable(0x01),
        TeamAndRaceNotSelectable(0x03),
        RaceFixedToRandom(0x04),
        AutomatedMatchMaking(0xCC)

        int value

        static Mode of(int value) {
            EnumUtils.of(Mode.class, value)
        }
    }


    GameStartRecord(ByteReader reader) {
        byte id = reader.nextByte()
        if (id != RECORD_ID) {
            throw new W3gParserException("Invalid record id: $id, when expected $RECORD_ID")
        }
        totalSize = reader.nextWord()
        int numberOfSlots = reader.nextByte()
        for (int i = 0; i < numberOfSlots; i++) {
            slots.add(new SlotRecord(reader))
        }
        randomSeed = reader.nextDWord()
        mode = Mode.of(reader.nextByteAsInt())
        numberOfPossibleSpawns = reader.nextByte()
    }
}
