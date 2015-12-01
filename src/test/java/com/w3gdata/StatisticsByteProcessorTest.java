package com.w3gdata;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatisticsByteProcessorTest {

    @Test
    public void nullTerminationNullFirst() {
        byte[] buf = {0, 1, 2, 3};
        assertEquals(0, StatisticsByteProcessor.findNullTermination(buf, 0));

    }

    @Test
    public void nullTerminationNullLast() {
        byte[] buf = {1, 2, 3, 0};
        assertEquals(3, StatisticsByteProcessor.findNullTermination(buf, 0));
    }

    @Test
    public void nullTerminationNullMiddle() {
        byte[] buf = {1, 2, 0, 3, 0};
        assertEquals(2, StatisticsByteProcessor.findNullTermination(buf, 0));
    }
}