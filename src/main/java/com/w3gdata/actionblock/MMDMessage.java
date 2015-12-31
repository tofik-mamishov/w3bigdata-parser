package com.w3gdata.actionblock;

/**
 * - Messages can be identified by the surrounding pattern: kMMD.Dat[null]val:[decimal-number][null][message contents][null][dword]"
 * - Checksum messages can be identified by the surrounding pattern: kMMD.Dat[null]chk:[decimal-number][null][decimal-number][null][dword]"
 */
public class MMDMessage implements ActionBlock {
    public static final int ID = 0x6B;
    public String name;
    public String checksum;
    public String secondChecksum;
    public int weakChecksum;

    @Override
    public int getId() {
        return ID;
    }
}
