package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

public class GameSpeed implements Action {

    public static final byte SLOW = 0x00;

    public static final byte NORMAL = 0x01;

    public static final byte FAST = 0x02;

    public static final int ID = 0x03;

    public final int speed;

    public GameSpeed(int speed) {
        this.speed = speed;
    }

    public GameSpeed() {
        speed = 0;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteBuffer buf) {
        return new GameSpeed(buf.readByte());
    }
}
