package com.w3gdata;

import static com.w3gdata.util.ByteUtils.debugToFile;

import com.google.common.primitives.Bytes;
import com.jcraft.jzlib.Inflater;
import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZStream;
import com.w3gdata.util.ByteReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import org.apache.log4j.Logger;

public class DataBlockReader {

    private static final Logger logger = Logger.getLogger(DataBlockReader.class);

    private final ByteReader reader;
    private boolean debugMode;

    public DataBlockReader(ByteReader reader) {
        this.reader = reader;
    }

    public byte[] decompress() {
        try {
            logger.info("Inflating data blocks...");
            List<DataBlock> blocks = new ArrayList<>();
            while (reader.hasMore()) {
                DataBlock block = readDataBlock(reader);
                blocks.add(block);
            }
            logger.info("Read " + blocks.size() + " blocks.");
            byte[] result = concatenate(blocks);
            if (isDebugMode()) {
                debugToFile(result, "decompressed.bin");
            }
            return result;
        } catch (DataFormatException e) {
            throw new W3gParserException(e.getMessage(), e);
        }
    }

    private DataBlock readDataBlock(ByteReader reader) throws DataFormatException {
        DataBlockHeader dataBlockHeader = new DataBlockHeader(reader);
        return inflateDataBlock(reader, dataBlockHeader);
    }

    private DataBlock inflateDataBlock(ByteReader reader, DataBlockHeader header) {
        byte[] decompressed = new byte[header.decompressedSize];
        Inflater inflater = new Inflater();
        inflater.setInput(reader.getBuf(), reader.offset(), header.size, true);
        inflater.setOutput(decompressed);
        int decompressionError = inflater.init();
        checkErrors(inflater, decompressionError, "inflateInit");
        while (moreToInflate(header, inflater)) {
            inflater.avail_in = inflater.avail_out = 1; /* force small buffers */
            decompressionError = inflater.inflate(JZlib.Z_SYNC_FLUSH);
            if (decompressionError == JZlib.Z_STREAM_END) {
                break;
            }
            checkErrors(inflater, decompressionError, "inflate");
        }
        reader.forward(header.size);
        return new DataBlock(header, decompressed);
    }

    private static boolean moreToInflate(DataBlockHeader dataBlockHeader, Inflater inflater) {
        return inflater.total_out < dataBlockHeader.decompressedSize &&
                inflater.total_in < dataBlockHeader.size;
    }

    private byte[] concatenate(List<DataBlock> blocks) {
        int totalSize = blocks.stream().mapToInt(value -> value.decompressed.length).sum();
        logger.info("Total inflated fixedSize is: " + totalSize);
        //TODO: Any suggestions to make it more functional?
        byte[][] concatenated = new byte[blocks.size()][];
        for (int i = 0; i < blocks.size(); i++) {
            concatenated[i] = blocks.get(i).decompressed;
        }
        return Bytes.concat(concatenated);
    }

    private static void checkErrors(ZStream z, int err, String msg) {
        if (err != JZlib.Z_OK) {
            if (z.msg != null) {
                logger.error(z.msg + " ");
            }
            logger.error(msg + " error: " + err);
            throw new W3gParserException(msg + " error: " + err);
        }
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
}
