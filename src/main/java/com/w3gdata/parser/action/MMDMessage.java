package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

/**
 * - Messages can be identified by the surrounding pattern: kMMD.Dat[null]val:[decimal-number][null][message contents][null][dword]"
 * - Checksum messages can be identified by the surrounding pattern: kMMD.Dat[null]chk:[decimal-number][null][decimal-number][null][dword]"
 */
public class MMDMessage implements Action {
    public static final int ID = 0x6B;
    public String name;
    public String checksum;
    public String secondChecksum;
    public int weakChecksum;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Action deserialize(ByteBuffer buf) {
        MMDMessage block = new MMDMessage();
        block.name = buf.readNullTerminatedString();
        block.checksum = buf.readNullTerminatedString();
        block.secondChecksum = buf.readNullTerminatedString();
        block.weakChecksum = buf.readDWord();
        return block;
    }
}
