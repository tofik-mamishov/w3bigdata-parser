package com.w3gdata.parser.actionblock;

public class TransferResources implements ActionBlock {
    public static final int ID = 0x51;

    public int playerSlotNumber;

    public int goldToTransfer;

    public int lumberToTransfer;

    @Override
    public int getId() {
        return ID;
    }
}
