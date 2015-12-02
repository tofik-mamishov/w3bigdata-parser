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

    private static final String REPLAY_SOURCE_FILE_NAME = "w3replayTestFile.w3g";

    private static final int EXPECTED_PLAYERS_NUMBER = 2;

    private static final String EXPECTED_PLAYER_NAME_1 = "ZEIKOOO";

    private static final String EXPECTED_PLAYER_NAME_2 = "adoveov";

    private static final String EXPECTED_GAME_MODE = "1vs1";

    private static final String EXPECTED_MAP = "w3arena__turtlerock__v3";

    private static int EXPECTED_MATCH_LENGTH_MS = (45*60 + 24) * 1000 + 150;

    private W3gInfo w3gInfo;
    private ByteSource replaySourceFile;
    private W3gParser parser;

    @Before
    public void setUp() throws Exception {
        URL resourceURL = Resources.getResource(REPLAY_SOURCE_FILE_NAME);
        replaySourceFile = Resources.asByteSource(resourceURL);
        parser = new W3gParser();
    }


    @Test
    public void testParseHeader() throws Exception {
        try {
            w3gInfo = parser.parse(replaySourceFile);
            ReplayInformation.Header expectedHeader = new ReplayInformation.Header();
            expectedHeader.headerVersion = 1;
            expectedHeader.size = (int) replaySourceFile.size();
            expectedHeader.firstDataBlockOffset = 68;

            ReplayInformation.SubHeader expectedSubHeader = new ReplayInformation.SubHeader();
            expectedSubHeader.versionNumber = 26;
            expectedSubHeader.buildNumber = 6059;
            expectedSubHeader.flags = 0x8000;
            expectedSubHeader.timeLength = EXPECTED_MATCH_LENGTH_MS;
            ReplayInformation expectedReplayInformation = new ReplayInformation();
            expectedReplayInformation.header = expectedHeader;
            expectedReplayInformation.subHeader = expectedSubHeader;

            PlayerRecord host = new PlayerRecord();
            host.playerId = 2;
            host.recordId = 0;
            host.name = EXPECTED_PLAYER_NAME_1;
            W3gInfo expectedData = new W3gInfo();
            expectedData.replayInformation = expectedReplayInformation;
            expectedData.host = host;
            assertEquals(expectedData, w3gInfo);
        } catch (ParseException e) {
            fail("Should never happen!");
        }
        assertNotNull(w3gInfo);
    }

    @Test
    public void testReadPlayersNumber() throws Exception {
        assertEquals(EXPECTED_PLAYERS_NUMBER, w3gInfo.getPlayerRecords().size());
    }

    @Test
    public void testReadGameMode() throws Exception {
        assertEquals(EXPECTED_GAME_MODE, w3gInfo.getGameMode());
    }

    @Test
    public void testReadMap() throws Exception {
        assertEquals(EXPECTED_MAP, w3gInfo.getMap());
    }
}

