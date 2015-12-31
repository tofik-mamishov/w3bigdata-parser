package com.w3gdata;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import com.w3gdata.actionblock.ActionBlock;
import com.w3gdata.actionblock.PauseGame;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
        List<ActionBlock> allActionBlocks = w3gInfo.getAllActionBlocks();
        Optional<ActionBlock> pauseGame = allActionBlocks.stream().filter(a -> a instanceof PauseGame).findFirst();
        assertTrue(pauseGame.isPresent());
    }



}
