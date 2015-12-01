package com.w3gdata;

import com.google.common.base.Joiner;
import com.google.common.io.ByteProcessor;
import com.google.common.io.Files;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.*;

public class W3gParcerTest {

    private static final Logger logger = Logger.getLogger(W3gParcerTest.class);

    private static final String REPLY_SOURCE_FILE_NAME = "w3replayTestFile.w3g";

    private static final int EXPECTED_PLAYERS_NUMBER = 2;

    private static final String EXPECTED_PLAYER_NAME_1 = "ZEIKOOO";

    private static final String EXPECTED_PLAYER_NAME_2 = "adoveov";

    private static final String EXPECTED_GAME_MODE = "1vs1";

    private static final String EXPECTED_MAP = "w3arena__turtlerock__v3";

    private static final String EXPECTED_MATCH_LENGTH = "00:45:24";

    private StatisticsData statisticsData;

    private File replaySourceFile;

    @Before
    public void setUp() throws Exception {
        replaySourceFile = new File(getClass().getResource(REPLY_SOURCE_FILE_NAME).toURI());
    }

    @Test
    public void playingWithFire() throws IOException {
        Files.asByteSource(replaySourceFile).read(new ByteProcessor<Long>() {
            @Override
            public boolean processBytes(byte[] buf, int off, int len) throws IOException {
                logger.debug(Joiner.on(" ").join(buf.length, off, len));
                return off < buf.length;
            }

            @Override
            public Long getResult() {
                return null;
            }
        });

    }

    @Test
    public void testParse() throws Exception {
        W3gParcer w3gParcer = new W3gParcer();
        try {
            statisticsData = w3gParcer.parse(replaySourceFile);
        } catch (ParseException e) {
            fail("Should never happen!");
        }
        assertNotNull(statisticsData);
    }

    @Test
    public void testReadPlayersNumber() throws Exception {
        assertEquals(EXPECTED_PLAYERS_NUMBER, statisticsData.getPlayers().size());
    }

    @Test
    public void testReadPlayer1() throws Exception {
        assertEquals(EXPECTED_PLAYER_NAME_1, statisticsData.getPlayers().get(0).getName());
    }

    @Test
    public void testReadPlayer2() throws Exception {
        assertEquals(EXPECTED_PLAYER_NAME_2, statisticsData.getPlayers().get(1).getName());
    }

    @Test
    public void testReadGameMode() throws Exception {
        assertEquals(EXPECTED_GAME_MODE, statisticsData.getGameMode());
    }

    @Test
    public void testReadMap() throws Exception {
        assertEquals(EXPECTED_MAP, statisticsData.getMap());
    }

    @Test
    public void testReadMatchLength() throws Exception {
        assertEquals(EXPECTED_MATCH_LENGTH, statisticsData.getMatchLength());

    }
}
