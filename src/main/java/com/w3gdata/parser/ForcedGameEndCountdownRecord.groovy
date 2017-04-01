package com.w3gdata.parser

import com.w3gdata.util.ByteReader
import com.w3gdata.util.EnumUtils
import com.w3gdata.util.Valued
import groovy.transform.TupleConstructor

import java.time.Duration


class ForcedGameEndCountdownRecord {
    public Mode mode
    public Duration countdown

    @TupleConstructor
    static enum Mode implements Valued {
        CountdownIsRunning(0x00),
        CountdownIsOver(0x01)

        int value

        static Mode of(int value) {
            EnumUtils.of(Mode.class, value)
        }
    }

    ForcedGameEndCountdownRecord(ByteReader reader) {
        mode = Mode.of(reader.nextDWord())
        countdown = Duration.ofMillis(reader.nextDWord())
    }
}
