package com.w3gdata.parser

import com.w3gdata.util.ByteReader
import com.w3gdata.util.EnumUtils
import com.w3gdata.util.Valued
import groovy.transform.Canonical
import groovy.transform.TupleConstructor

@Canonical
class PlayerRecord {
    public static final int POSSIBLE_RECORD_ID = 0x00000110
    public PlayerType type
    public byte playerId
    public String name
    public GameType gameType
    public Race race

    @TupleConstructor
    static enum Race implements Valued {
        Human(0x01),
        Orc(0x02),
        NightElf(0x04),
        Undead(0x08),
        Daemon(0x10),
        Random(0x20),
        SelectableOrFixed(0x40)

        int value

        static Race of(int value) {
            EnumUtils.of(Race.class, value)
        }
    }

    @TupleConstructor
    static enum PlayerType implements Valued {
        GameHost(0x00),
        AdditionalPlayer(0x16)

        int value

        static PlayerType of(int value) {
            EnumUtils.of(PlayerType.class, value)
        }
    }

    @TupleConstructor
    static enum GameType implements Valued {
        Custom(0x01, 1),
        Ladder(0x16, 4)

        int value
        int size

        static GameType of(int value) {
            EnumUtils.of(GameType.class, value)
        }
    }

    PlayerRecord(ByteReader reader) {
        int possiblyRecordId = reader.nextDWord()
        if (possiblyRecordId != POSSIBLE_RECORD_ID) {
            throw new W3gParserException("Unknown player record first 4 bytes, expected $POSSIBLE_RECORD_ID but was $possiblyRecordId")
        }
        type = PlayerType.of(reader.nextByte())
        playerId = reader.nextByte()
        name = reader.nextNullTerminatedString()
        gameType = GameType.of(reader.nextByteAsInt())
        reader.forward(gameType.size)
        if (gameType == GameType.Ladder) {
            race = Race.of(reader.nextDWord())
        }
    }
}
