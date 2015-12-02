package com.w3gdata;

import com.google.common.primitives.Bytes;
import com.jcraft.jzlib.Inflater;
import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZStream;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.zip.DataFormatException;

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
        byte[] decompressed = inflateDataBlocks(buf);
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

    private byte[] inflateDataBlocks(byte[] buf) throws DataFormatException {
        logger.info("Inflating data blocks...");
        int nextBlockOffset = data.replayInformation.header.firstDataBlockOffset;
        List<DataBlock> blocks = new ArrayList<DataBlock>();
        while (nextBlockOffset + HEADER_SIZE < buf.length) {
            DataBlock block = readDataBlock(buf, data.replayInformation.header.firstDataBlockOffset);
            nextBlockOffset += DataBlock.Header.SIZE + block.header.size;
            blocks.add(block);
        }
        logger.info("Read " + blocks.size() + " blocks.");
        int totalSize = blocks.stream().mapToInt(new ToIntFunction<DataBlock>() {
            @Override
            public int applyAsInt(DataBlock value) {
                return value.decompressed.length;
            }
        }).reduce(0, Integer::sum);
        logger.info("Total inflated size is: " + totalSize);
        //Any suggestions to make it more functional?
        byte[][] concatenated = new byte[blocks.size()][];
        for (int i = 0; i < blocks.size(); i++) {
            concatenated[i] = blocks.get(i).decompressed;
        }
        return Bytes.concat(concatenated);
    }

    private DataBlock readDataBlock(byte[] buf, int offset) throws DataFormatException {
        int decompressionError;
        DataBlock block = new DataBlock();
        block.header.size = readWord(buf, offset);
        block.header.decompressedSize = readWord(buf, offset + 0x0002);
        block.decompressed = new byte[block.header.decompressedSize];
        Inflater inflater = new Inflater();
        inflater.setInput(buf, offset + DataBlock.Header.SIZE, block.header.size, true);
        inflater.setOutput(block.decompressed);
        decompressionError = inflater.init();
        CHECK_ERR(inflater, decompressionError, "inflateInit");
        while (inflater.total_out < block.header.decompressedSize &&
                inflater.total_in < block.header.size) {
            inflater.avail_in = inflater.avail_out = 1; /* force small buffers */
            decompressionError = inflater.inflate(JZlib.Z_SYNC_FLUSH);
            if (decompressionError == JZlib.Z_STREAM_END) break;
            CHECK_ERR(inflater, decompressionError, "inflate");
        }
        return block;
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


    public static int readDWord(byte[] buf, int offset) {
        return buf[offset + 3] << 24 | (buf[offset + 2] & 0xFF) << 16 | (buf[offset + 1] & 0xFF) << 8 | (buf[offset] & 0xFF);
    }

    public static int readWord(byte[] buf, int offset) {
        return (buf[offset + 1] & 0xFF) << 8 | (buf[offset] & 0xFF);
    }

    public static String readString(byte[] buf, int offset, int len) {
        return new String(buf, offset, len, Charset.defaultCharset());
    }

    public static String readNullTerminatedString(byte[] buf, int offset) {
        int len = findNullTermination(buf, offset) - offset;
        return readString(buf, offset, len);
    }

    public static int findNullTermination(byte[] buf, int offset) {
        int i = offset;
        while(buf[i++] != 0);
        return i - 1;
    }

    public static void CHECK_ERR(ZStream z, int err, String msg) {
        if (err != JZlib.Z_OK) {
            if (z.msg != null) logger.error(z.msg + " ");
            logger.error(msg + " error: " + err);
            throw new ProcessorException(msg + " error: " + err);
        }
    }
}
