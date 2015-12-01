package com.w3gdata;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.text.ParseException;

import static org.junit.Assert.*;

public class W3GParserTest {

    private static final Logger logger = Logger.getLogger(W3GParserTest.class);

    private static final String REPLY_SOURCE_FILE_NAME = "w3replayTestFile.w3g";

    private static final int EXPECTED_PLAYERS_NUMBER = 2;

    private static final String EXPECTED_PLAYER_NAME_1 = "ZEIKOOO";

    private static final String EXPECTED_PLAYER_NAME_2 = "adoveov";

    private static final String EXPECTED_GAME_MODE = "1vs1";

    private static final String EXPECTED_MAP = "w3arena__turtlerock__v3";

    private static int EXPECTED_MATCH_LENGTH_MS = (45*60 + 24) * 1000 + 150;

    private StatisticsData statisticsData;
    private ByteSource replaySourceFile;
    private W3gParser parser;

    @Before
    public void setUp() throws Exception {
        URL resourceURL = Resources.getResource(REPLY_SOURCE_FILE_NAME);
        replaySourceFile = Resources.asByteSource(resourceURL);
        parser = new W3gParser();
    }


    @Test
    public void testParseHeader() throws Exception {
        try {
            statisticsData = parser.parse(replaySourceFile);
            ReplayInformation.Header expectedHeader = new ReplayInformation.Header();
            expectedHeader.headerVersion = 1;
            expectedHeader.size = (int) replaySourceFile.size();

            ReplayInformation.SubHeader expectedSubHeader = new ReplayInformation.SubHeader();
            expectedSubHeader.versionNumber = 26;
            expectedSubHeader.buildNumber = 6059;
            expectedSubHeader.flags = 0x8000;
            expectedSubHeader.timeLength = EXPECTED_MATCH_LENGTH_MS;
            ReplayInformation expectedReplayInformation = new ReplayInformation();
            expectedReplayInformation.header = expectedHeader;
            expectedReplayInformation.subHeader = expectedSubHeader;
            StatisticsData expectedData = new StatisticsData();
            expectedData.replayInformation = expectedReplayInformation;
            assertEquals(expectedData, statisticsData);
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
}

