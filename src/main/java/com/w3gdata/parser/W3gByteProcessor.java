package com.w3gdata.parser;

import com.w3gdata.util.ByteReader;
import com.w3gdata.util.ByteUtils;
import org.apache.log4j.Logger;

import java.util.zip.DataFormatException;

public class W3gByteProcessor {

    private static final Logger logger = Logger.getLogger(W3gByteProcessor.class);

    private static final int HEADER_SUBHEADER_OFFSET = 0x0030;
    public static final int PLAYER_RECORD_OFFSET = 0x0004;

    private W3gInfo data = new W3gInfo();
    private ByteReader decompressed;

    public W3gInfo process(byte[] buf) throws DataFormatException {
        ByteReader reader = new ByteReader(buf, 0);
        readHeaders(reader);
        DataBlockReader dataBlockReader = new DataBlockReader(reader);
        decompressed = new ByteReader(dataBlockReader.decompress(), PLAYER_RECORD_OFFSET);
        data.host = readPlayerRecord();
        data.gameName = decompressed.nextNullTerminatedString();
        decompressed.forward(1);
        readGameSettings();
        data.playerCount = decompressed.nextDWord();
        data.gameType = decompressed.nextDWord();
        data.languageId = decompressed.nextDWord();
        readPlayerList();
        readGameStartRecord();
        readReplayData();
        return data;
    }

    private void readGameStartRecord() {
        data.gameStartRecord.id = decompressed.nextByte();
        decompressed.forward(2);
        int numberOfSlots = decompressed.nextByte();
        for (int i = 0; i < numberOfSlots; i++) {
            data.gameStartRecord.slots.add(readSlot());
        }
        decompressed.forward(4);
        data.gameStartRecord.mode = decompressed.nextByte();
        data.gameStartRecord.startSpotCount = decompressed.nextByte();
    }

    private GameStartRecord.SlotRecord readSlot() {
        GameStartRecord.SlotRecord slot = new GameStartRecord.SlotRecord();
        slot.id = decompressed.nextByte();
        slot.mapDownloadPercent = decompressed.nextByte();
        slot.status = decompressed.nextByte();
        slot.computerPlayerFlag = decompressed.nextByte();
        slot.teamNumber = decompressed.nextByte();
        slot.color = decompressed.nextByte();
        slot.playerRaceFlags = decompressed.nextByte();
        slot.computerAIStrength = decompressed.nextByte();
        slot.handicap = decompressed.nextByte();
        return slot;
    }

    private void readPlayerList() {
        while (decompressed.peek() == 0x16) {
            data.getPlayerRecords().add(readPlayerRecord());
            decompressed.forward(4);
        }
    }

    private PlayerRecord readPlayerRecord() {
        logger.info("Reading player record...");
        PlayerRecord playerRecord = new PlayerRecord();
        playerRecord.recordId = decompressed.nextByte();
        playerRecord.playerId = decompressed.nextByte();
        playerRecord.name = decompressed.nextNullTerminatedString();
        playerRecord.additionalData.size = decompressed.nextByte();
        if (playerRecord.additionalData.size == 1) {
            decompressed.forward(1);
        } else {
            decompressed.forward(4);
            playerRecord.additionalData.race = decompressed.nextDWord();
        }
        return playerRecord;
    }

    private void readGameSettings() {
        int currentOffset = decompressed.offset();
        byte[] decoded = new EncodedStringDecoder().decode(decompressed.getBuf(), currentOffset, decompressed.findNullTermination() - currentOffset);
        decompressed.forward(decoded.length + 1);
        int pos = 0;
        data.gameSettings.speed = decoded[pos++];
        data.gameSettings.visibilityRules = decoded[pos++];
        data.gameSettings.teamRules = decoded[pos++];
        data.gameSettings.gameRules = decoded[pos++];
        pos += 9;
        data.gameSettings.mapName = ByteUtils.readNullTerminatedString(decoded, pos);
        pos += data.gameSettings.mapName.length() + 1;
        data.gameSettings.creatorName = ByteUtils.readNullTerminatedString(decoded, pos);
        decompressed.forward(decoded.length + 1);
    }


    private int readHeaders(ByteReader byteReader) {
        logger.info("Reading header information...");
        ReplayHeader replayHeader = new ReplayHeader(byteReader);

        logger.info("Reading sub header information...");
        ReplaySubHeader subheader = new ReplaySubHeader(byteReader);

        data.replayInformation = new ReplayInformation(replayHeader, subheader);
        return byteReader.offset();
    }

    private void readReplayData() {
        ReplayDataReader reader = new ReplayDataReader(data, decompressed);
        reader.read();
    }
}
