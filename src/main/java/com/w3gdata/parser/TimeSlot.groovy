package com.w3gdata.parser

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import com.w3gdata.util.ByteReader

import java.time.Duration


class TimeSlot {
    public final int n
    public final Duration timeIncrement
    public final Multimap<Byte, Command> commandDataBlocks

    TimeSlot(ByteReader reader) {
        n = reader.nextWord()
        timeIncrement = Duration.ofMillis(reader.nextWord())
        commandDataBlocks = readCommandDataBlocks(reader)
    }

    private Multimap<Byte, Command> readCommandDataBlocks(ByteReader reader) {
        int limit = reader.offset() + n - 2
        Multimap<Byte, Command> playerCommands = ArrayListMultimap.create()
        reader.listOfUntil(limit, { it -> new Command(it)}).each {
            playerCommands.put(it.playerId, it)
        }
        return playerCommands
    }
}
