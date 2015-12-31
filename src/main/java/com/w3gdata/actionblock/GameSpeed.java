package com.w3gdata.actionblock;

public class GameSpeed implements ActionBlock {

    public static final byte SLOW = 0x00;

    public static final byte NORMAL = 0x01;

    public static final byte FAST = 0x02;

    public static final int ID = 0x03;

    public int speed;

    public GameSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int getId() {
        return ID;
    }
}
