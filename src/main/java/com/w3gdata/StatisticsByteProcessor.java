package com.w3gdata;

import com.google.common.io.ByteProcessor;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;

public class StatisticsByteProcessor implements ByteProcessor<StatisticsData> {

    private static final Logger logger = Logger.getLogger(StatisticsByteProcessor.class);

    /**
     * Header
     */
    private static final int HEADER_TITLE_OFFSET = 0x0000;

    private static final String HEADER_TITLE = "Warcraft III recorded game\0x1A\0";

    private static final int HEADER_TITLE_LENGTH = HEADER_TITLE.length();

    private static final int HEADER_SIZE_OFFSET = 0x001c;

    private static final int HEADER_SIZE_LE_1_06 = 0x40;

    private static final int HEADER_SIZE_ME_1_07_AND_TFT = 0x44;

    private static final int HEADER_COMPRESSED_FILE_SIZE_OFFSET = 0x0020;

    private static final int HEADER_FILE_VERSION_OFFSET = 0x0024;

    private static final int HEADER_FILE_VERSION_LE_1_06 = 0x00;

    private static final int HEADER_FILE_VERSION_ME_1_07_AND_TFT = 0x01;

    private static final int HEADER_DECOMPRESSED_DATA_SIZE_OFFSET = 0x0028;

    private static final int HEADER_COMPRESSED_DATA_BLOCKS_NUMBER_OFFSET = 0x002c;

    private static final int HEADER_SUBHEADER_OFFSET = 0x0030;

    private State state = State.HEADER;
    private StatisticsData result = new StatisticsData();

    private enum State {
        HEADER {
            protected State process(StatisticsData data, byte[] buf, int off, int len) {
                logger.info("Reading header information...");
                data.replayInformation.header.size = readDWord(buf, HEADER_COMPRESSED_FILE_SIZE_OFFSET);
                data.replayInformation.header.headerVersion = readDWord(buf, HEADER_FILE_VERSION_OFFSET);

                logger.info("Reading sub header information...");
                data.replayInformation.subHeader.versionNumber = readDWord(buf, HEADER_SUBHEADER_OFFSET + 0x0004);
                data.replayInformation.subHeader.buildNumber = readWord(buf, HEADER_SUBHEADER_OFFSET + 0x0008);
                data.replayInformation.subHeader.flags = readWord(buf, HEADER_SUBHEADER_OFFSET + 0x000A);
                data.replayInformation.subHeader.timeLength = readDWord(buf, HEADER_SUBHEADER_OFFSET + 0x000C);
                return DATA_BLOCK_HEADER;
            }
        },
        DATA_BLOCK_HEADER {
            protected State process(StatisticsData data, byte[] buf, int off, int len) {
                return END;
            }
        },
        DATA_BLOCK {
            protected State process(StatisticsData data, byte[] buf, int off, int len) {
                return END;
            }
        },
        END {
            protected State process(StatisticsData data, byte[] buf, int off, int len) {
                throw new IllegalStateException("We are done by now! Stap!");
            }
        };
        protected abstract State process(StatisticsData data, byte[] buf, int off, int len);
    }

    @Override
    public boolean processBytes(byte[] buf, int off, int len) throws IOException {
        state = state.process(result, buf, off, len);
        return state != State.END;
    }

    @Override
    public StatisticsData getResult() {
        return result;
    }

    private static int readDWord(byte[] buf, int offset) {
        return buf[offset + 3] << 24 | (buf[offset + 2] & 0xFF) << 16 | (buf[offset + 1] & 0xFF) << 8 | (buf[offset] & 0xFF);
    }

    private static int readWord(byte[] buf, int offset) {
        return (buf[offset + 1] & 0xFF) << 8 | (buf[offset] & 0xFF);
    }

    private static String readString(byte[] buf, int offset, int len) {
        return new String(buf, 0, 26, Charset.defaultCharset());
    }
}
