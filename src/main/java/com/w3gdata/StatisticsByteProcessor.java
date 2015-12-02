package com.w3gdata;

import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;

import static com.w3gdata.util.ByteUtils.*;

public class StatisticsByteProcessor {

    private static final Logger logger = Logger.getLogger(StatisticsByteProcessor.class);

    private static final int HEADER_FIRST_DATA_BLOCK_OFFSET = 0x001C;
    private static final int HEADER_COMPRESSED_FILE_SIZE_OFFSET = 0x0020;
    private static final int HEADER_FILE_VERSION_OFFSET = 0x0024;
    private static final int HEADER_SUBHEADER_OFFSET = 0x0030;
    private static final int HEADER_SIZE = 0x44;
    public static final int PLAYER_RECORD_OFFSET = 0x0004;

    private StatisticsData data = new StatisticsData();

    public StatisticsData process(byte[] buf) throws DataFormatException {
        readHeaders(buf);
        DataBlocksDecompressor decompressor = new DataBlocksDecompressor(buf, data.replayInformation.header.firstDataBlockOffset);
        byte[] decompressed = decompressor.decompress();
//        debugToFile(decompressed);
        data.host = readPlayerRecord(decompressed, PLAYER_RECORD_OFFSET);
        return data;
    }

    private PlayerRecord readPlayerRecord(byte[] buf, int offset) {
        PlayerRecord playerRecord = new PlayerRecord();
        int pos = offset;
        playerRecord.recordId = buf[pos++];
        playerRecord.playerId = buf[pos++];
        playerRecord.name = readNullTerminatedString(buf, pos);
        pos += playerRecord.name.length() + 1;
        return playerRecord;
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

    private void debugToFile(byte[] decompressed) {
        try (FileOutputStream fos = new FileOutputStream("decompressed.bin")) {
            fos.write(decompressed);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
