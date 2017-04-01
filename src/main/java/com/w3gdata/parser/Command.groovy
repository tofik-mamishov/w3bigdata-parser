package com.w3gdata.parser

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import com.w3gdata.parser.action.Action
import com.w3gdata.parser.action.ActionType
import com.w3gdata.util.ByteReader


class Command {
    public final byte playerId
    public final int actionBlockLength
    public final Multimap<ActionType, Action> actionBlocks

    Command(ByteReader reader) {
        playerId = reader.nextByte()
        actionBlockLength = reader.nextWord()
        actionBlocks = readActionBlocks(reader)
    }

    private Multimap<ActionType, Action> readActionBlocks(ByteReader reader) {
        int limit = actionBlockLength + reader.offset()
        Multimap<ActionType, Action> actionBlocks = ArrayListMultimap.create()
        reader.listOfUntil(limit, { it ->
            int id = it.nextByte()
            ActionType.getById(id)
        }).each { actionType ->
            actionBlocks.put(actionType, actionType.read(reader))}
        return actionBlocks
    }
}
