package com.w3gdata;

import org.apache.log4j.Logger;

import java.nio.charset.Charset;

public class StatisticsByteProcessor {

    private static final Logger logger = Logger.getLogger(StatisticsByteProcessor.class);

    private static final int HEADER_FIRST_DATA_BLOCK_OFFSET = 0x001C;
    private static final int HEADER_COMPRESSED_FILE_SIZE_OFFSET = 0x0020;
    private static final int HEADER_FILE_VERSION_OFFSET = 0x0024;
    private static final int HEADER_SUBHEADER_OFFSET = 0x0030;
    private static final int HEADER_SIZE = 0x44;

    private StatisticsData data = new StatisticsData();

    public StatisticsData process(byte[] buf) {
        readHeaders(buf);
        return data;
    }

    private void readHeaders(byte[] buf) {
        logger.info("Reading header information...");
        data.replayInformation.header.size = readDWord(buf, HEADER_COMPRESSED_FILE_SIZE_OFFSET);
        data.replayInformation.header.firstDataBlockOffset = readDWord(buf, HEADER_FIRST_DATA_BLOCK_OFFSET);
        data.replayInformation.header.headerVersion = readDWord(buf, HEADER_FILE_VERSION_OFFSET);

        logger.info("Reading sub header information...");
        data.replayInformation.subHeader.versionNumber = readDWord(buf, HEADER_SUBHEADER_OFFSET + 0x0004);
        data.replayInformation.subHeader.buildNumber = readWord(buf, HEADER_SUBHEADER_OFFSET + 0x0008);
        data.replayInformation.subHeader.flags = readWord(buf, HEADER_SUBHEADER_OFFSET + 0x000A);
        data.replayInformation.subHeader.timeLength = readDWord(buf, HEADER_SUBHEADER_OFFSET + 0x000C);
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
