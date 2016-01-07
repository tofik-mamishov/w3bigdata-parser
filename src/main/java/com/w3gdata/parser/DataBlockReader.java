package com.w3gdata.parser;

import com.google.common.primitives.Bytes;
import com.jcraft.jzlib.Inflater;
import com.jcraft.jzlib.JZlib;
import com.w3gdata.parser.util.ByteUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.zip.DataFormatException;

public class DataBlockReader {

    private static final Logger logger = Logger.getLogger(DataBlockReader.class);

    private final byte[] buf;
    private final int firstBlockOffset;
    private final List<DataBlock> blocks = new ArrayList<>();

    public DataBlockReader(byte[] buf, int firstBlockOffset) {
        this.buf = buf;
        logger.debug("Given " + buf.length + " bytes.");
        this.firstBlockOffset = firstBlockOffset;
    }

    public byte[] decompress() {
        try {
            logger.debug("Inflating data blocks...");
            int nextBlockOffset = firstBlockOffset;
            while (nextBlockOffset + firstBlockOffset < buf.length) {
                DataBlock block = readDataBlock(buf, nextBlockOffset);
                nextBlockOffset += DataBlock.Header.SIZE + block.header.size;
                blocks.add(block);
            }
            logger.debug("Read " + blocks.size() + " blocks.");

            return concatenate();
        } catch (DataFormatException e) {
            throw new W3gParserException(e.getMessage(), e);
        }
    }


    private DataBlock readDataBlock(byte[] buf, int offset) throws DataFormatException {
        DataBlock block = new DataBlock();
        block.header.size = ByteUtils.readWord(buf, offset);
        block.header.decompressedSize = ByteUtils.readWord(buf, offset + 0x0002);
        block.header.checksum = ByteUtils.readDWord(buf, offset + 0x0004);
        block.decompressed = new byte[block.header.decompressedSize];
        inflate(buf, offset, block);
        ByteUtils.debugToFile(block.decompressed, "first_block_decompressed.bin");
        ByteUtils.debugToFile(block.decompressed, 0, 0xe6, "first_block_decompressed_meaning.bin");

        ByteUtils.debugToFile(buf, offset, block.header.size + DataBlock.Header.SIZE, "first_block_compressed_with_headers.bin");
        byte[] firstBlockFullCompressed = new byte[2 + 2 + 4 + block.header.size];
        buf[offset + 0x0004] = 0;
        buf[offset + 0x0004 + 1] = 0;
        buf[offset + 0x0004 + 2] = 0;
        buf[offset + 0x0004 + 3] = 0;
        System.arraycopy(buf, offset, firstBlockFullCompressed, 0, firstBlockFullCompressed.length);
        ByteUtils.debugToFile(firstBlockFullCompressed, "first_block_compressed_with_header_zeroes_at_crc.bin");

        return block;
    }

    private void inflate(byte[] buf, int offset, DataBlock block) {
        Inflater inflater = new Inflater();
        inflater.setInput(buf, offset + DataBlock.Header.SIZE, block.header.size, true);
        inflater.setOutput(block.decompressed);

        int decompressionError = inflater.init();
        DataBlockUtils.checkErrors(inflater, decompressionError, "inflateInit");
        while (moreToInflate(block, inflater)) {
            inflater.avail_in = inflater.avail_out = 1; /* force small buffers */
            decompressionError = inflater.inflate(JZlib.Z_SYNC_FLUSH);
            if (decompressionError == JZlib.Z_STREAM_END) break;
            DataBlockUtils.checkErrors(inflater, decompressionError, "inflate");
        }
    }

    private static boolean moreToInflate(DataBlock block, Inflater inflater) {
        return inflater.total_out < block.header.decompressedSize &&
                inflater.total_in < block.header.size;
    }

    private byte[] concatenate() {
        int totalSize = blocks.stream().mapToInt(new ToIntFunction<DataBlock>() {
            @Override
            public int applyAsInt(DataBlock value) {
                return value.decompressed.length;
            }
        }).sum();
        logger.debug("Total inflated is: " + totalSize);
        byte[][] concatenated = new byte[blocks.size()][];
        for (int i = 0; i < blocks.size(); i++) {
            concatenated[i] = blocks.get(i).decompressed;
        }
        return Bytes.concat(concatenated);
    }

    public int getFirstBlockOffset() {
        return firstBlockOffset;
    }

    public List<DataBlock> getBlocks() {
        return blocks;
    }
}
