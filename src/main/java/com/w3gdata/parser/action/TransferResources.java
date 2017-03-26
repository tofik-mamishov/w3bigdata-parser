package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

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
    public Action deserialize(ByteBuffer buf) {
        TransferResources transferResources = new TransferResources();
        transferResources.playerSlotNumber = buf.readByte();
        transferResources.goldToTransfer = buf.readDWord();
        transferResources.lumberToTransfer = buf.readDWord();
        return transferResources;
    }
}
