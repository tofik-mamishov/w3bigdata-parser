package com.w3gdata;

import com.jcraft.jzlib.Inflater;
import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZStream;
import org.apache.log4j.Logger;

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

    private StatisticsData data = new StatisticsData();

    public StatisticsData process(byte[] buf) throws DataFormatException {
        readHeaders(buf);
        List<DataBlock> blocks = readDataBlocks(buf);
        int totalSize = blocks.stream().mapToInt(new ToIntFunction<DataBlock>() {
            @Override
            public int applyAsInt(DataBlock value) {
                return value.decompressed.length;
            }
        }).reduce(0, Integer::sum);
        logger.info("Total decompressed size is: " + totalSize);
        return data;
    }

    private List<DataBlock> readDataBlocks(byte[] buf) throws DataFormatException {
        logger.info("Reading data blocks...");
        int nextBlockOffset = data.replayInformation.header.firstDataBlockOffset;
        List<DataBlock> blocks = new ArrayList<DataBlock>();
        while (nextBlockOffset + HEADER_SIZE < buf.length) {
            DataBlock block = readDataBlock(buf, data.replayInformation.header.firstDataBlockOffset);
            nextBlockOffset += DataBlock.Header.SIZE + block.header.size;
            blocks.add(block);
        }
        logger.info("Read " + blocks.size() + " blocks.");
        return blocks;
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

    private static int readDWord(byte[] buf, int offset) {
        return buf[offset + 3] << 24 | (buf[offset + 2] & 0xFF) << 16 | (buf[offset + 1] & 0xFF) << 8 | (buf[offset] & 0xFF);
    }

    private static int readWord(byte[] buf, int offset) {
        return (buf[offset + 1] & 0xFF) << 8 | (buf[offset] & 0xFF);
    }

    private static String readString(byte[] buf, int offset, int len) {
        return new String(buf, 0, 26, Charset.defaultCharset());
    }

    static void CHECK_ERR(ZStream z, int err, String msg) {
        if (err != JZlib.Z_OK) {
            if (z.msg != null) System.out.print(z.msg + " ");
            System.out.println(msg + " error: " + err);
            System.exit(1);
        }
    }
}
