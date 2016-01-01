package com.w3gdata;

import com.google.common.primitives.Bytes;
import com.jcraft.jzlib.Deflater;
import com.jcraft.jzlib.Inflater;
import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZStream;
import com.w3gdata.util.ByteUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.zip.DataFormatException;

import static com.w3gdata.util.ByteUtils.*;

public class DataBlockReader {

    private static final Logger logger = Logger.getLogger(DataBlockReader.class);

    private final byte[] buf;
    private final int firstBlockOffset;
    private boolean debugMode;

    public DataBlockReader(byte[] buf, int firstBlockOffset) {
        this.buf = buf;
        logger.info("Given " + buf.length + " bytes.");
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

            byte[] bytes = writeDataBlocks(blocks);
            ByteUtils.debugToFile(bytes, "all_blocks.bin");
            return concatenate(blocks);
        } catch (DataFormatException e) {
            throw new W3gParserException(e.getMessage(), e);
        }
    }

    private DataBlock readDataBlock(byte[] buf, int offset) throws DataFormatException {
        DataBlock block = new DataBlock();
        block.header.size = readWord(buf, offset);
        block.header.decompressedSize = readWord(buf, offset + 0x0002);
        block.header.checksum = readDWord(buf, offset + 0x0004);
        block.decompressed = new byte[block.header.decompressedSize];
        inflate(buf, offset, block);
//        debugToFile(buf, offset, block.header.size, "first_block_compressed.bin");
        return block;
    }

    private byte[] writeDataBlocks(List<DataBlock> blocks) {
        int totalSize = blocks.stream().map(DataBlock::getHeader).mapToInt(DataBlock.Header::getSize).sum();
        byte[] result = new byte[totalSize];
        int offset = 0;
        logger.info("Calculated total size: " + totalSize);
        int index = 0;
        for (DataBlock block: blocks) {
            byte[] bytes = writeDataBlock(block);
            ByteUtils.writeBytes(result, offset, bytes);
            offset += bytes.length;
            logger.info("Index: " + index);
            index++;
        }
        return result;
    }

    private byte[] writeDataBlock(DataBlock block) {
        byte[] result = new byte[block.header.size + DataBlock.Header.SIZE];
        writeWord(result, 0x0000, block.header.size);
        writeWord(result, 0x0002, block.header.decompressedSize);
        writeDWord(result, 0x0004, block.header.checksum);
        writeBytes(result, DataBlock.Header.SIZE, deflate(block.decompressed, block));
        return result;
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

    private byte[] deflate(byte[] decompressed, DataBlock block) {
        byte[] buf = new byte[block.header.size];
        Deflater deflater = new Deflater();
        deflater.init(JZlib.Z_BEST_SPEED);
        deflater.setInput(decompressed, 0, decompressed.length, true);
        deflater.setOutput(buf);
        int compressionError;
        while (true) {
            deflater.avail_out = 1;
            compressionError = deflater.deflate(JZlib.Z_FINISH);
            if (compressionError == JZlib.Z_STREAM_END) break;
            checkErrors(deflater, compressionError, "deflate");
        }

        compressionError = deflater.end();
        checkErrors(deflater, compressionError, "deflateEnd");
        return buf;
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
        logger.info("Total inflated is: " + totalSize);
        //TODO: Any suggestions to make it more functional?
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
