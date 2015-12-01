import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;

public class W3gParcer {

    /**
     * Header
     */

    private static final int HEADER_TITLE_OFFSET = 0x0000;

    private static final String HEADER_TITLE = "Warcraft III recorded game\0x1A\0";

    private static final int HEADER_TITLE_LENGTH = HEADER_TITLE.length();

    private static final int HEADER_SIZE_OFFSET = 0x001c;

    private static final int HEADER_SIZE_LE_1_06 = 0x40;

    private static final int HEADER_SIZE_ME_1_07_AND_TFT = 0x44;

    private static final int HEADER_FILE_SIZE_OFFSET = 0x0020;

    private static final int HEADER_FILE_VERSION_OFFSET = 0x0024;

    private static final int HEADER_FILE_VERSION_LE_1_06 = 0x00;

    private static final int HEADER_FILE_VERSION_ME_1_07_AND_TFT = 0x01;

    public StatisticsData parse(File replySourceFile) throws ParseException, FileNotFoundException {
        StatisticsData result = new StatisticsData();
        FileInputStream fileInputStream = new FileInputStream(replySourceFile);

        return result;
    }
}
