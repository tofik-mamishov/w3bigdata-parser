package com.w3gdata.parser

import com.w3gdata.util.ByteReader
import com.w3gdata.util.EnumUtils
import com.w3gdata.util.Valued
import groovy.transform.Canonical
import groovy.transform.TupleConstructor

@Canonical
class GameSettings {
    public Speed speed
    public EnumSet<Visibility> visibilities
    public TeamRule teamRule
    public EnumSet<GameRule> gameRules
    public String mapName
    public String creatorName
    public int crc

    @TupleConstructor
    static enum Speed implements Valued {
        Slow(0), Normal(1), Fast(2)

        int value

        static Speed of(int value) {
            EnumUtils.of(Speed.class, value)
        }
    }

    static enum Visibility {
        HideTerrain {
            boolean is(BigInteger bitField) {
                bitField.testBit(0)
            }
        },
        MapExplored {
            boolean is(BigInteger bitField) {
                bitField.testBit(1)
            }
        },
        AlwaysVisible {
            boolean is(BigInteger bitField) {
                bitField.testBit(2)
            }
        },
        Default {
            boolean is(BigInteger bitField) {
                bitField.testBit(3)
            }
        },
        OffOrReferees {
            boolean is(BigInteger bitField) {
                !bitField.testBit(4) && !bitField.testBit(5)
            }
        },
        Unused {
            boolean is(BigInteger bitField) {
                !bitField.testBit(4) && bitField.testBit(5)
            }
        },
        ObsOnDefeat {
            boolean is(BigInteger bitField) {
                bitField.testBit(4) && !bitField.testBit(5)
            }
        },
        OnOrReferees {
            boolean is(BigInteger bitField) {
                bitField.testBit(4) && bitField.testBit(5)
            }
        },
        TeamsTogether {
            boolean is(BigInteger bitField) {
                bitField.testBit(6)
            }
        }

        abstract boolean is(BigInteger bitField)

        static EnumSet<Visibility> of(byte value) {
            EnumSet<Visibility> result = EnumSet.noneOf(Visibility.class)
            BigInteger field = BigInteger.valueOf(value)
            for (Visibility visibility : values()) {
                if (visibility.is(field)) {
                    result.add(visibility)
                }
            }
            return result
        }
    }

    @TupleConstructor
    static enum TeamRule implements Valued {
        Off(0), On(3), Unknown(6)

        int value

        static TeamRule of(int value) {
            EnumUtils.of(TeamRule.class, value)
        }
    }

    static enum GameRule {
        FullSharedUnit {
            boolean is(BigInteger bitField) {
                bitField.testBit(0)
            }
        },
        RandomHero {
            boolean is(BigInteger bitField) {
                bitField.testBit(1)
            }
        },
        RandomRaces {
            boolean is(BigInteger bitField) {
                bitField.testBit(2)
            }
        },
        Observer {
            boolean is(BigInteger bitField) {
                bitField.testBit(6)
            }
        },

        abstract boolean is(BigInteger bitField)

        static EnumSet<GameRule> of(byte value) {
            EnumSet<GameRule> result = EnumSet.noneOf(GameRule.class)
            BigInteger field = BigInteger.valueOf(value)
            for (GameRule gameRule : values()) {
                if (gameRule.is(field)) {
                    result.add(gameRule)
                }
            }
            return result
        }
    }

    GameSettings(ByteReader reader) {
        byte[] decoded = new EncodedStringReader(reader).decode()
        ByteReader decodedReader = new ByteReader(decoded)
        speed = Speed.of(decodedReader.nextByteAsInt())
        visibilities = Visibility.of(decodedReader.nextByte())
        teamRule = TeamRule.of(decodedReader.nextByteAsInt())
        gameRules = GameRule.of(decodedReader.nextByte())
        decodedReader.forward(5)
        crc = decodedReader.nextDWord()
        mapName = decodedReader.nextNullTerminatedString()
        creatorName = decodedReader.nextNullTerminatedString()
        reader.forward(decoded.length + 1)
    }
}
