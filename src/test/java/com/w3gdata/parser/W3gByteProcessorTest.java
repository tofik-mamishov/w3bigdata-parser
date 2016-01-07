package com.w3gdata.parser;

import com.w3gdata.parser.util.ByteUtils;
import org.junit.Assert;
import org.junit.Test;

public class W3gByteProcessorTest {

    @Test
    public void nullTerminationNullFirst() {
        byte[] buf = {0, 1, 2, 3};
        Assert.assertEquals(0, ByteUtils.findNullTermination(buf, 0));

    }

    @Test
    public void nullTerminationNullLast() {
        byte[] buf = {1, 2, 3, 0};
        Assert.assertEquals(3, ByteUtils.findNullTermination(buf, 0));
    }

    @Test
    public void nullTerminationNullMiddle() {
        byte[] buf = {1, 2, 0, 3, 0};
        Assert.assertEquals(2, ByteUtils.findNullTermination(buf, 0));
    }
}