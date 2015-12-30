package com.w3gdata;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class SimpleReplayTest {

    private static final String REPLAY_SOURCE_FILE_NAME = "altqq.w3g";

    private static W3gInfo w3gInfo;
    private static ByteSource replaySourceFile;
    private static W3gParser parser;

    @BeforeClass
    public static void setUp() throws Exception {
        URL resourceURL = Resources.getResource(REPLAY_SOURCE_FILE_NAME);
        replaySourceFile = Resources.asByteSource(resourceURL);
        parser = new W3gParser();
        w3gInfo = parser.parse(replaySourceFile);
        assertNotNull(w3gInfo);
    }

    @Test
    public void expectHavingPauseAction() throws Exception {



    }



}
