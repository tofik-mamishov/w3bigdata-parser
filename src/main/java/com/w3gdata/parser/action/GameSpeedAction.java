package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

public class GameSpeedAction implements Action {

    public static final byte SLOW = 0x00;

    public static final byte NORMAL = 0x01;

    public static final byte FAST = 0x02;

    public final int speed;

    public GameSpeedAction(ByteReader reader) {
        speed = reader.nextByte();
    }
}
