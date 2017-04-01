package com.w3gdata.parser;

import com.w3gdata.util.ByteReader;
import org.apache.log4j.Logger;

import java.util.zip.DataFormatException;

public class W3gByteProcessor {

    private static final Logger logger = Logger.getLogger(W3gByteProcessor.class);

    private W3gInfo data = new W3gInfo();
    private ByteReader reader;

    public W3gInfo process(byte[] buf) throws DataFormatException {
        ByteReader headerReader = new ByteReader(buf, 0);
        data.replayInformation = readHeaders(headerReader);
        DataBlockReader dataBlockReader = new DataBlockReader(headerReader);

        reader = new ByteReader(dataBlockReader.nextDataBlocks());
        data.host = new PlayerRecord(reader);

        data.gameName = reader.nextNullTerminatedStringAndForward();
        data.gameSettings = new GameSettings(reader);

        data.playerCount = reader.nextDWord();
        data.gameType = new GameType(reader);
        data.languageId = reader.nextDWord();
        readPlayerList();
        data.gameStartRecord = new GameStartRecord(reader);
        readReplayData();
        return data;
    }

    private void readPlayerList() {
        while (reader.peek() == PlayerRecord.ADDITIONAL_PLAYER_RECORD_ID) {
            data.getPlayerRecords().add(readPlayerRecord());
            reader.forward(4);
        }
    }

    private PlayerRecord readPlayerRecord() {
        logger.info("Reading player record...");
        return new PlayerRecord(reader);
    }

    private ReplayInformation readHeaders(ByteReader byteReader) {
        logger.info("Reading header information...");
        ReplayHeader replayHeader = new ReplayHeader(byteReader);

        logger.info("Reading sub header information...");
        ReplaySubHeader subheader = new ReplaySubHeader(byteReader);

        return new ReplayInformation(replayHeader, subheader);
    }

    private void readReplayData() {
        ReplayDataReader reader = new ReplayDataReader(data, this.reader);
        reader.read();
    }
}
