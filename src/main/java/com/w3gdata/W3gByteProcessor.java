package com.w3gdata;

import com.w3gdata.util.ByteBuffer;
import com.w3gdata.util.ByteUtils;
import org.apache.log4j.Logger;

import java.util.zip.DataFormatException;

public class W3gByteProcessor {

    private static final Logger logger = Logger.getLogger(W3gByteProcessor.class);

    private static final int HEADER_FIRST_DATA_BLOCK_OFFSET = 0x001C;
    private static final int HEADER_COMPRESSED_FILE_SIZE_OFFSET = 0x0020;
    private static final int HEADER_FILE_VERSION_OFFSET = 0x0024;
    private static final int HEADER_SUBHEADER_OFFSET = 0x0030;
    public static final int PLAYER_RECORD_OFFSET = 0x0004;

    private W3gInfo data = new W3gInfo();
    private ByteBuffer decompressed;

    public W3gInfo process(byte[] buf) throws DataFormatException {
        readHeaders(buf);
        DataBlockReader reader = new DataBlockReader(buf, data.replayInformation.header.firstDataBlockOffset);
        decompressed = new ByteBuffer(reader.decompress(), PLAYER_RECORD_OFFSET);
        data.host = readPlayerRecord();
        data.gameName = decompressed.readNullTerminatedString();
        decompressed.increment(1);
        readGameSettings();
        data.playerCount = decompressed.readDWord();
        data.gameType = decompressed.readDWord();
        data.languageId = decompressed.readDWord();
        readPlayerList();
        readGameStartRecord();
        readReplayData();
        return data;
    }

    private void readGameStartRecord() {
        data.gameStartRecord.id = decompressed.readByte();
        decompressed.increment(2);
        int numberOfSlots = decompressed.readByte();
        for (int i = 0; i < numberOfSlots; i++) {
            data.gameStartRecord.slots.add(readSlot());
        }
        decompressed.increment(4);
        data.gameStartRecord.mode = decompressed.readByte();
        data.gameStartRecord.startSpotCount = decompressed.readByte();
    }

    private GameStartRecord.SlotRecord readSlot() {
        GameStartRecord.SlotRecord slot = new GameStartRecord.SlotRecord();
        slot.id = decompressed.readByte();
        slot.mapDownloadPercent = decompressed.readByte();
        slot.status = decompressed.readByte();
        slot.computerPlayerFlag = decompressed.readByte();
        slot.teamNumber = decompressed.readByte();
        slot.color = decompressed.readByte();
        slot.playerRaceFlags = decompressed.readByte();
        slot.computerAIStrength = decompressed.readByte();
        slot.handicap = decompressed.readByte();
        return slot;
    }

    private void readPlayerList() {
        while (decompressed.peek() == 0x16) {
            data.getPlayerRecords().add(readPlayerRecord());
            decompressed.increment(4);
        }
    }

    private PlayerRecord readPlayerRecord() {
        logger.info("Reading player record...");
        PlayerRecord playerRecord = new PlayerRecord();
        playerRecord.recordId = decompressed.readByte();
        playerRecord.playerId = decompressed.readByte();
        playerRecord.name = decompressed.readNullTerminatedString();
        playerRecord.additionalData.size = decompressed.readByte();
        if (playerRecord.additionalData.size == 1) {
            decompressed.increment(1);
        } else {
            decompressed.increment(4);
            playerRecord.additionalData.race = decompressed.readDWord();
        }
        return playerRecord;
    }

    private void readGameSettings() {
        int currentOffset = decompressed.getOffset();
        byte[] decoded = new EncodedStringDecoder().decode(decompressed.getBuf(), currentOffset, decompressed.findNullTermination() - currentOffset);
        int pos = 0;
        data.gameSettings.speed = decoded[pos++];
        data.gameSettings.visibilityRules = decoded[pos++];
        data.gameSettings.teamRules = decoded[pos++];
        data.gameSettings.gameRules = decoded[pos++];
        pos += 9;
        data.gameSettings.mapName = ByteUtils.readNullTerminatedString(decoded, pos);
        pos += data.gameSettings.mapName.length() + 1;
        data.gameSettings.creatorName = ByteUtils.readNullTerminatedString(decoded, pos);
        decompressed.increment(decoded.length + 1);
    }


    private void readHeaders(byte[] buf) {
        logger.info("Reading header information...");
        data.replayInformation.header.firstDataBlockOffset = ByteUtils.readDWord(buf, HEADER_FIRST_DATA_BLOCK_OFFSET);
        data.replayInformation.header.size = ByteUtils.readDWord(buf, HEADER_COMPRESSED_FILE_SIZE_OFFSET);
        data.replayInformation.header.headerVersion = ByteUtils.readDWord(buf, HEADER_FILE_VERSION_OFFSET);
        if (data.replayInformation.header.headerVersion != 0x01) {
            throw new W3gParserException("Old replays are not supported!");
        }

        logger.info("Reading sub header information...");
        data.replayInformation.subHeader.versionNumber = ByteUtils.readDWord(buf, HEADER_SUBHEADER_OFFSET + 0x0004);
        data.replayInformation.subHeader.buildNumber = ByteUtils.readWord(buf, HEADER_SUBHEADER_OFFSET + 0x0008);
        data.replayInformation.subHeader.flags = ByteUtils.readWord(buf, HEADER_SUBHEADER_OFFSET + 0x000A);
        data.replayInformation.subHeader.timeLength = ByteUtils.readDWord(buf, HEADER_SUBHEADER_OFFSET + 0x000C);
    }

    private void readReplayData() {
        ReplayDataReader reader = new ReplayDataReader(data, decompressed);
        reader.read();
    }
}
