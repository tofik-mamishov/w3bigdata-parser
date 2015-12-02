package com.w3gdata;

import com.google.common.primitives.Bytes;
import com.jcraft.jzlib.Inflater;
import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZStream;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.zip.DataFormatException;

import static com.w3gdata.util.ByteUtils.debugToFile;
import static com.w3gdata.util.ByteUtils.readWord;

public class DataBlockReader {

    private static final Logger logger = Logger.getLogger(DataBlockReader.class);

    private final byte[] buf;
    private final int firstBlockOffset;
    private boolean debugMode;

    public DataBlockReader(byte[] buf, int firstBlockOffset) {
        this.buf = buf;
        this.firstBlockOffset = firstBlockOffset;
    }

    public byte[] decompress() {
        try {
            logger.info("Inflating data blocks...");
            int nextBlockOffset = firstBlockOffset;
            List<DataBlock> blocks = new ArrayList<>();
            while (nextBlockOffset + firstBlockOffset < buf.length) {
                DataBlock block = readDataBlock(buf, nextBlockOffset);
                nextBlockOffset += DataBlock.Header.SIZE + block.header.size;
                blocks.add(block);
            }
            logger.info("Read " + blocks.size() + " blocks.");
            byte[] result = concatenate(blocks);
            if (isDebugMode()) {
                debugToFile(result, "decompressed.bin");
            }
            return result;
        } catch (DataFormatException e) {
            throw new ProcessorException(e.getMessage(), e);
        }
    }

    private DataBlock readDataBlock(byte[] buf, int offset) throws DataFormatException {
        DataBlock block = new DataBlock();
        block.header.size = readWord(buf, offset);
        block.header.decompressedSize = readWord(buf, offset + 0x0002);
        block.decompressed = new byte[block.header.decompressedSize];
        inflate(buf, offset, block);
        return block;
    }

    private void inflate(byte[] buf, int offset, DataBlock block) {
        Inflater inflater = new Inflater();
        inflater.setInput(buf, offset + DataBlock.Header.SIZE, block.header.size, true);
        inflater.setOutput(block.decompressed);
        int decompressionError = inflater.init();
        checkErrors(inflater, decompressionError, "inflateInit");
        while (moreToInflate(block, inflater)) {
            inflater.avail_in = inflater.avail_out = 1; /* force small buffers */
            decompressionError = inflater.inflate(JZlib.Z_SYNC_FLUSH);
            if (decompressionError == JZlib.Z_STREAM_END) break;
            checkErrors(inflater, decompressionError, "inflate");
        }
    }

    private static boolean moreToInflate(DataBlock block, Inflater inflater) {
        return inflater.total_out < block.header.decompressedSize &&
                inflater.total_in < block.header.size;
    }

    private byte[] concatenate(List<DataBlock> blocks) {
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

    private static void checkErrors(ZStream z, int err, String msg) {
        if (err != JZlib.Z_OK) {
            if (z.msg != null) logger.error(z.msg + " ");
            logger.error(msg + " error: " + err);
            throw new ProcessorException(msg + " error: " + err);
        }
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
}
