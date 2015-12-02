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

import static com.w3gdata.util.ByteUtils.readWord;

public class DataBlocksDecompressor {

    private static final Logger logger = Logger.getLogger(DataBlocksDecompressor.class);

    private final byte[] buf;
    private final int firstBlockOffset;

    public DataBlocksDecompressor(byte[] buf, int firstBlockOffset) {
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
            return concatenate(blocks);
        } catch (DataFormatException e) {
            e.printStackTrace();
            throw new ProcessorException(e.getMessage(), e);
        }
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
        checkErrors(inflater, decompressionError, "inflateInit");
        while (inflater.total_out < block.header.decompressedSize &&
                inflater.total_in < block.header.size) {
            inflater.avail_in = inflater.avail_out = 1; /* force small buffers */
            decompressionError = inflater.inflate(JZlib.Z_SYNC_FLUSH);
            if (decompressionError == JZlib.Z_STREAM_END) break;
            checkErrors(inflater, decompressionError, "inflate");
        }
        return block;
    }

    private static void checkErrors(ZStream z, int err, String msg) {
        if (err != JZlib.Z_OK) {
            if (z.msg != null) logger.error(z.msg + " ");
            logger.error(msg + " error: " + err);
            throw new ProcessorException(msg + " error: " + err);
        }
    }
}
