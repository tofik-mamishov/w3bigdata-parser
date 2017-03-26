package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class TransferResources implements Action {
    public static final int ID = 0x51;

    public int playerSlotNumber;

    public int goldToTransfer;

    public int lumberToTransfer;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteReader buf) {
        TransferResources transferResources = new TransferResources();
        transferResources.playerSlotNumber = buf.nextByte();
        transferResources.goldToTransfer = buf.nextDWord();
        transferResources.lumberToTransfer = buf.nextDWord();
        return transferResources;
    }
}
