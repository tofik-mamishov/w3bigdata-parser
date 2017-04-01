package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;

/**
 * - Messages can be identified by the surrounding pattern: kMMD.Dat[null]val:[decimal-number][null][message contents][null][dword]"
 * - Checksum messages can be identified by the surrounding pattern: kMMD.Dat[null]chk:[decimal-number][null][decimal-number][null][dword]"
 */
public class MMDMessage implements Action {
    public String name;
    public String checksum;
    public String secondChecksum;
    public int weakChecksum;

    public MMDMessage(ByteReader reader) {
        name = reader.nextNullTerminatedString();
        checksum = reader.nextNullTerminatedString();
        secondChecksum = reader.nextNullTerminatedString();
        weakChecksum = reader.nextDWord();
    }
}
