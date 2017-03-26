package com.w3gdata

import com.w3gdata.util.ByteReader
import com.w3gdata.util.EnumUtils
import com.w3gdata.util.Valued
import groovy.transform.Canonical
import groovy.transform.ToString

import java.time.Duration

/*
offset | size/type | Description
-------+-----------+-----------------------------------------------------------
0x0000 |  1 dword  | version identifier string reading:
       |           |  'WAR3' for WarCraft III Classic
       |           |  'W3XP' for WarCraft III Expansion Set 'The Frozen Throne'
       |           | (note that this string is saved in little endian format
       |           |  in the replay file)
0x0004 |  1 dword  | version number (corresponds to patch 1.xx so far)
0x0008 |  1  word  | build number (see section 2.3)
0x000A |  1  word  | flags
       |           |   0x0000 for single player games
       |           |   0x8000 for multiplayer games (LAN or Battle.net)
0x000C |  1 dword  | replay length in msec
0x0010 |  1 dword  | CRC32 checksum for the header
       |           | (the checksum is calculated for the complete header
       |           |  including this field which is set to zero)
 */
@Canonical
@ToString(includePackage = false)
class ReplaySubHeader {
    public Version version
    public GameType gameType
    public Duration timeLength
    public int crc

    enum GameType implements Valued {
        SINGLE_PLAYER(0x0000),
        MULTI_PLAYER(0x8000)

        final int value

        GameType(int value) {
            this.value = value
        }

        static GameType of(int value) {
            EnumUtils.of(GameType.class, value)
        }
    }

    ReplaySubHeader(ByteReader reader) {
        version = new Version(reader)
        gameType = GameType.of(reader.nextWord())
        timeLength = Duration.ofMillis(reader.nextDWord())
        crc = reader.nextDWord()
    }

    ReplaySubHeader(Version version, GameType gameType, Duration timeLength, int crc) {
        this.version = version
        this.gameType = gameType
        this.timeLength = timeLength
        this.crc = crc
    }
}