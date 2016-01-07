package com.w3gdata.parser;

import java.util.ArrayList;
import java.util.List;

public class GameStartRecord {

    public byte id;
    public byte mode;
    public byte startSpotCount;
    public List<SlotRecord> slots = new ArrayList<>();

    public static class SlotRecord {
        public byte id;
        public byte mapDownloadPercent;
        public byte status;
        public byte computerPlayerFlag;
        public byte teamNumber;
        public byte color;
        public byte playerRaceFlags;
        public byte computerAIStrength;
        public byte handicap;
    }

}
