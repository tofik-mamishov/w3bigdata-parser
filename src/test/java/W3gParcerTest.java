import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class W3gParcerTest {

    private static final String REPLY_SOURCE_FILE_NAME = "w3replayTestFile.w3g";

    private static final int EXPECTED_PLAYERS_NUMBER = 2;

    private static final String EXPECTED_PLAYER_NAME_1 = "ZEIKOOO";

    private static final String EXPECTED_PLAYER_NAME_2 = "adoveov";

    private static final String EXPECTED_GAME_MODE = "1vs1";

    private static final String EXPECTED_MAP = "w3arena__turtlerock__v3";

    private static final String EXPECTED_MATCH_LENGTH = "00:45:24";

    private File replySourceFile;

    @Before
    public void setUp() throws Exception {
        replySourceFile = new File(getClass().getResource(REPLY_SOURCE_FILE_NAME).toURI());
    }

    @Test
    public void testParse() throws Exception {
        W3gParcer w3gParcer = new W3gParcer();
        StatisticsData statisticsData = w3gParcer.parse(replySourceFile);
        assertEquals(EXPECTED_PLAYERS_NUMBER, statisticsData.getPlayers().size());
        assertEquals(EXPECTED_PLAYER_NAME_1, statisticsData.getPlayers().get(0).getName());
        assertEquals(EXPECTED_PLAYER_NAME_2, statisticsData.getPlayers().get(1).getName());
        assertEquals(EXPECTED_GAME_MODE, statisticsData.getGameMode());
        assertEquals(EXPECTED_MAP, statisticsData.getMap());
        assertEquals(EXPECTED_MATCH_LENGTH, statisticsData.getMatchLength());
    }
}
