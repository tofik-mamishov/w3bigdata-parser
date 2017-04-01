package com.w3gdata.parser

import com.w3gdata.util.ByteReader
import com.w3gdata.util.EnumUtils
import com.w3gdata.util.Valued
import groovy.transform.TupleConstructor


class LeaveGameRecord {
    public Reason reason
    public byte playerId
    public Result result

    @TupleConstructor
    static enum Reason implements Valued {
        ConnectionClosedByRemoteGame(0x01),
        ConnectionClosedByLocalGame(0x0C),
        Unknown(0x0E)

        int value

        static Reason of(int value) {
            EnumUtils.of(Reason.class, value)
        }
    }

    @TupleConstructor
    static enum Result implements Valued {
        PlayerDisconnected(0x01),
        PlayerLeft(0x07),
        PlayerWasCompletelyErased(0x08),
        PlayerWon(0x09),
        Draw(0x0A),
        ObserverLeft(0x0B)

        int value

        static Result of(int value) {
            EnumUtils.of(Result.class, value)
        }
    }

    LeaveGameRecord(ByteReader reader) {
        reason = Reason.of(reader.nextDWord())
        playerId = reader.nextByte()
        result = Result.of(reader.nextDWord())
        reader.forward(4)
    }
}
