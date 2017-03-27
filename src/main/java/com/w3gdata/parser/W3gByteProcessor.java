package com.w3gdata.parser;

import com.w3gdata.util.ByteReader;
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

        data.gameName = dataReader.nextNullTerminatedStringAndForward();
        data.gameSettings = new GameSettings(dataReader);

        data.playerCount = dataReader.nextDWord();
        data.gameType = new GameType(dataReader);
        data.languageId = dataReader.nextDWord();
        readPlayerList();
        readGameStartRecord();
        readReplayData();
        return data;
    }

    private void readGameStartRecord() {
        data.gameStartRecord = new GameStartRecord(dataReader);
    }

    private void readPlayerList() {
        while (dataReader.peek() == PlayerRecord.ADDITIONAL_PLAYER_RECORD_ID) {
            data.getPlayerRecords().add(readPlayerRecord());
            dataReader.forward(4);
        }
    }

    private PlayerRecord readPlayerRecord() {
        logger.info("Reading player record...");
        return new PlayerRecord(dataReader);
    }

    private void readGameSettings() {
        data.gameSettings = new GameSettings(dataReader);
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
