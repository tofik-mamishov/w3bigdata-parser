package com.w3gdata.parser

import com.w3gdata.util.ByteReader
import com.w3gdata.util.EnumUtils
import com.w3gdata.util.Valued
import groovy.transform.TupleConstructor


class SlotRecord {
    public byte playerId
    public byte mapDownloadPercent
    public Status status
    public Intellect intellect
    public int teamNumber
    public boolean isObserverOrReferee
    public Color color
    public Race race
    public Strength intellectStrength
    public int handicap

    @TupleConstructor
    static enum Status implements Valued {
        Empty(0x00), Closed(0x01), Used(0x02)

        int value

        static Status of(int value) {
            EnumUtils.of(Status.class, value)
        }
    }

    @TupleConstructor
    static enum Intellect implements Valued {
        Human(0x00), Computer(0x01)

        int value

        static Intellect of(int value) {
            EnumUtils.of(Intellect.class, value)
        }
    }

    @TupleConstructor
    static enum Strength implements Valued {
        Easy(0x00), NormalOrHuman(0x01), Insane(0x02)

        int value

        static Strength of(int value) {
            EnumUtils.of(Strength.class, value)
        }
    }

    @TupleConstructor
    static enum Color {
        Red,
        Blue,
        Cyan,
        Purple,
        Yellow,
        Orange,
        Green,
        Pink,
        Gray,
        LightBlue,
        DarkGreen,
        Brown

        static Color of(int value) {
            values()[value]
        }
    }


    SlotRecord(ByteReader reader) {
        playerId = reader.nextByte()
        mapDownloadPercent = reader.nextByte()
        status = Status.of(reader.nextByteAsInt())
        intellect = Intellect.of(reader.nextByteAsInt())
        teamNumber = reader.nextByteAsInt()
        if (teamNumber < 0 || teamNumber > 12) {
            throw new W3gParserException("Invalid teamNumber $teamNumber, expected it to be in 0-12 (inclusive) range")
        }
        isObserverOrReferee = teamNumber == 12

        color = Color.of(reader.nextByteAsInt())
        race = new Race(reader.nextByteAsInt())
        intellectStrength = Strength.of(reader.nextByteAsInt())
        handicap = reader.nextByteAsInt()
    }
}
