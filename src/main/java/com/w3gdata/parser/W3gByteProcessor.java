package com.w3gdata.parser;

import com.w3gdata.util.ByteReader;
import com.w3gdata.util.ByteUtils;
import org.apache.log4j.Logger;

import java.util.zip.DataFormatException;

public class W3gByteProcessor {

    private static final Logger logger = Logger.getLogger(W3gByteProcessor.class);

    private W3gInfo data = new W3gInfo();
    private ByteReader dataReader;

    public W3gInfo process(byte[] buf) throws DataFormatException {
        ByteReader headerReader = new ByteReader(buf, 0);
        data.replayInformation = readHeaders(headerReader);
        DataBlockReader dataBlockReader = new DataBlockReader(headerReader);
        dataReader = new ByteReader(dataBlockReader.nextDataBlocks());
        data.host = new PlayerRecord(dataReader);
        data.gameName = dataReader.nextNullTerminatedString();
        dataReader.forward(1);
        readGameSettings();
        data.playerCount = dataReader.nextDWord();
        data.gameType = dataReader.nextDWord();
        data.languageId = dataReader.nextDWord();
        readPlayerList();
        readGameStartRecord();
        readReplayData();
        return data;
    }

    private void readGameStartRecord() {
        data.gameStartRecord.id = dataReader.nextByte();
        dataReader.forward(2);
        int numberOfSlots = dataReader.nextByte();
        for (int i = 0; i < numberOfSlots; i++) {
            data.gameStartRecord.slots.add(readSlot());
        }
        dataReader.forward(4);
        data.gameStartRecord.mode = dataReader.nextByte();
        data.gameStartRecord.startSpotCount = dataReader.nextByte();
    }

    private GameStartRecord.SlotRecord readSlot() {
        GameStartRecord.SlotRecord slot = new GameStartRecord.SlotRecord();
        slot.id = dataReader.nextByte();
        slot.mapDownloadPercent = dataReader.nextByte();
        slot.status = dataReader.nextByte();
        slot.computerPlayerFlag = dataReader.nextByte();
        slot.teamNumber = dataReader.nextByte();
        slot.color = dataReader.nextByte();
        slot.playerRaceFlags = dataReader.nextByte();
        slot.computerAIStrength = dataReader.nextByte();
        slot.handicap = dataReader.nextByte();
        return slot;
    }

    private void readPlayerList() {
        while (dataReader.peek() == 0x16) {
            data.getPlayerRecords().add(readPlayerRecord());
            dataReader.forward(4);
        }
    }

    private PlayerRecord readPlayerRecord() {
        logger.info("Reading player record...");
        PlayerRecord playerRecord = new PlayerRecord(dataReader);
        return playerRecord;
    }

    private void readGameSettings() {
        int currentOffset = dataReader.offset();
        byte[] decoded = new EncodedStringDecoder().decode(dataReader.getBuf(), currentOffset, dataReader.findNullTermination() - currentOffset);
        int pos = 0;
        data.gameSettings.speed = decoded[pos++];
        data.gameSettings.visibilityRules = decoded[pos++];
        data.gameSettings.teamRules = decoded[pos++];
        data.gameSettings.gameRules = decoded[pos++];
        pos += 9;
        data.gameSettings.mapName = ByteUtils.readNullTerminatedString(decoded, pos);
        pos += data.gameSettings.mapName.length() + 1;
        data.gameSettings.creatorName = ByteUtils.readNullTerminatedString(decoded, pos);
        dataReader.forward(decoded.length + 1);
    }


    private ReplayInformation readHeaders(ByteReader byteReader) {
        logger.info("Reading header information...");
        ReplayHeader replayHeader = new ReplayHeader(byteReader);

        logger.info("Reading sub header information...");
        ReplaySubHeader subheader = new ReplaySubHeader(byteReader);

        return new ReplayInformation(replayHeader, subheader);
    }

    private void readReplayData() {
        ReplayDataReader reader = new ReplayDataReader(data, dataReader);
        reader.read();
    }
}
