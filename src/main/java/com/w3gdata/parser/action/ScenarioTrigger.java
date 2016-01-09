package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

public class ScenarioTrigger implements Action {
    public static final int ID = 0x62;

    public int unknownA;

    public int unknownB;

    public int unknownCounter;//only present for patch version >= 1.07

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteBuffer buf) {
        ScenarioTrigger scenarioTrigger = new ScenarioTrigger();
        scenarioTrigger.unknownA = buf.readDWord();
        scenarioTrigger.unknownB = buf.readDWord();
        scenarioTrigger.unknownCounter = buf.readDWord();
        return scenarioTrigger;
    }
}
