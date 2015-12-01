package com.w3gdata;

import com.google.common.io.ByteProcessor;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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


    @Override
    public boolean processBytes(byte[] buf, int off, int len) throws IOException {
        return off < buf.length;
    }

    @Override
    public StatisticsData getResult() {
        return null;
    }

    private static int readDWord(byte[] buf, int offset) {
        return ByteBuffer.wrap(buf, offset, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    private static int readWord(byte[] buf, int offset) {
        return ByteBuffer.wrap(buf, offset, 2).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    private static String readString(byte[] buf, int offset, int len) {
        return new String(buf, 0, 26, Charset.defaultCharset());
    }
}
