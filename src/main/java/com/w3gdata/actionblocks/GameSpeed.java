package com.w3gdata.actionblocks;

public class GameSpeed extends ActionBlock {

    public static final byte SLOW = 0x00;

    public static final byte NORMAL = 0x01;

    public static final byte FAST = 0x02;

    public final byte id = 0x03;

    public byte speed;
}
