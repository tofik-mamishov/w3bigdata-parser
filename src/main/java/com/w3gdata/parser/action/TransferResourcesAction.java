package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class TransferResourcesAction implements Action {
    public int playerSlotNumber;
    public int goldToTransfer;
    public int lumberToTransfer;

    public TransferResourcesAction(ByteReader reader) {
        playerSlotNumber = reader.nextByte();
        goldToTransfer = reader.nextDWord();
        lumberToTransfer = reader.nextDWord();
    }
}
