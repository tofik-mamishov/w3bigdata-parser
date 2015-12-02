package com.w3gdata;

import com.w3gdata.util.ByteUtils;
import org.apache.log4j.Logger;

import java.util.zip.DataFormatException;

import static com.w3gdata.util.ByteUtils.findNullTermination;

public class W3gByteProcessor {

    private static final Logger logger = Logger.getLogger(W3gByteProcessor.class);

    private static final int HEADER_FIRST_DATA_BLOCK_OFFSET = 0x001C;
    private static final int HEADER_COMPRESSED_FILE_SIZE_OFFSET = 0x0020;
    private static final int HEADER_FILE_VERSION_OFFSET = 0x0024;
    private static final int HEADER_SUBHEADER_OFFSET = 0x0030;
    public static final int PLAYER_RECORD_OFFSET = 0x0004;

    private W3gInfo data = new W3gInfo();
    private int offset;
    private byte[] decompressed;

    public W3gInfo process(byte[] buf) throws DataFormatException {
        readHeaders(buf);
        DataBlockReader reader = new DataBlockReader(buf, data.replayInformation.header.firstDataBlockOffset);
        decompressed = reader.decompress();
        offset = PLAYER_RECORD_OFFSET;
        data.host = readPlayerRecord();
        data.gameName = readNullTerminatedString();
        offset += 1;
        readGameSettings();
        data.playerCount = readDWord();
        data.gameType = readDWord();
        data.languageId = readDWord();
        readPlayerList();
        return data;
    }

    private void readPlayerList() {
        while (decompressed[offset] == 0x16) {
            data.getPlayerRecords().add(readPlayerRecord());
            offset += 4;
        }
    }

    private PlayerRecord readPlayerRecord() {
        logger.info("Reading player record...");
        PlayerRecord playerRecord = new PlayerRecord();
        playerRecord.recordId = decompressed[offset++];
        playerRecord.playerId = decompressed[offset++];
        playerRecord.name = readNullTerminatedString();
        playerRecord.additionalData.size = decompressed[offset++];
        if (playerRecord.additionalData.size == 1) {
            offset += 1;
        } else {
            offset += 4;
            playerRecord.additionalData.race = readDWord();
        }
        return playerRecord;
    }

    private void readGameSettings() {
        byte[] decoded = new EncodedStringDecoder().decode(decompressed, offset, findNullTermination(decompressed, offset) - offset);
        offset += decoded.length + 1;
        int pos = 0;
        data.gameSettings.speed = decoded[pos++];
        data.gameSettings.visibilityRules = decoded[pos++];
        data.gameSettings.teamRules = decoded[pos++];
        data.gameSettings.gameRules = decoded[pos++];
        pos += 9;
        data.gameSettings.mapName = ByteUtils.readNullTerminatedString(decoded, pos);
        pos += data.gameSettings.mapName.length() + 1;
        data.gameSettings.creatorName = ByteUtils.readNullTerminatedString(decoded, pos);
    }


    private void readHeaders(byte[] buf) {
        logger.info("Reading header information...");
        data.replayInformation.header.firstDataBlockOffset = ByteUtils.readDWord(buf, HEADER_FIRST_DATA_BLOCK_OFFSET);
        data.replayInformation.header.size = ByteUtils.readDWord(buf, HEADER_COMPRESSED_FILE_SIZE_OFFSET);
        data.replayInformation.header.headerVersion = ByteUtils.readDWord(buf, HEADER_FILE_VERSION_OFFSET);
        if (data.replayInformation.header.headerVersion != 0x01) {
            throw new ProcessorException("Old replays are not supported!");
        }

        logger.info("Reading sub header information...");
        data.replayInformation.subHeader.versionNumber = ByteUtils.readDWord(buf, HEADER_SUBHEADER_OFFSET + 0x0004);
        data.replayInformation.subHeader.buildNumber = ByteUtils.readWord(buf, HEADER_SUBHEADER_OFFSET + 0x0008);
        data.replayInformation.subHeader.flags = ByteUtils.readWord(buf, HEADER_SUBHEADER_OFFSET + 0x000A);
        data.replayInformation.subHeader.timeLength = ByteUtils.readDWord(buf, HEADER_SUBHEADER_OFFSET + 0x000C);
    }

    private int readDWord() {
        offset += 4;
        return ByteUtils.readDWord(decompressed, offset - 4);
    }

    private int readWord() {
        offset += 2;
        return ByteUtils.readDWord(decompressed, offset - 2);
    }

    private String readNullTerminatedString() {
        String result = ByteUtils.readNullTerminatedString(decompressed, offset);
        offset += result.length() + 1;
        return result;
    }

}
