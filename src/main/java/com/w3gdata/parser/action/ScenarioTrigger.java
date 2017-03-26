package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

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
    public Action deserialize(ByteReader buf) {
        ScenarioTrigger scenarioTrigger = new ScenarioTrigger();
        scenarioTrigger.unknownA = buf.nextDWord();
        scenarioTrigger.unknownB = buf.nextDWord();
        scenarioTrigger.unknownCounter = buf.nextDWord();
        return scenarioTrigger;
    }
}
