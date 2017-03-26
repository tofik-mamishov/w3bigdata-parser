package com.w3gdata.parser.util;

import com.w3gdata.util.ByteUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ByteUtilsTest {

    @Test
    public void testWriteWord() throws Exception {
        int value = 0x1234;
        byte[] buf = new byte[2];

        ByteUtils.writeWord(buf, 0, value);
        assertEquals(buf[0], 0x34);
        assertEquals(buf[1], 0x12);
    }

    @Test
    public void testWriteBytes() throws Exception {
        byte[] what = { 1, 2};
        byte[] buf = new byte[2];

        ByteUtils.writeBytes(buf, 0, what);
        assertEquals(buf[0], 1);
        assertEquals(buf[1], 2);
    }
}