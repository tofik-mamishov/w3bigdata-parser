package com.w3gdata.parser;

import com.jcraft.jzlib.Deflater;
import com.jcraft.jzlib.JZlib;
import com.w3gdata.parser.util.ByteUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.function.ToIntFunction;

public class DataBlockWriter {

    private static final Logger logger = Logger.getLogger(DataBlockWriter.class);

    private final List<DataBlock> blocks;

    public DataBlockWriter(List<DataBlock> blocks) {
        this.blocks = blocks;
    }

    public byte[] compress() {

//        changeFirstDataBlock();


        return writeDataBlocks();
    }

    public int getTotalSize() {
        return blocks.stream().map(DataBlock::getHeader).mapToInt(new ToIntFunction<DataBlock.Header>() {
            @Override
            public int applyAsInt(DataBlock.Header value) {
                return value.getSize() + DataBlock.Header.SIZE;
            }
        }).sum();
    }

    private void changeFirstDataBlock() {
        DataBlock dataBlock = blocks.get(0);
        byte b = dataBlock.decompressed[6];
        dataBlock.decompressed[6] = 'h';
    }

    private byte[] writeDataBlocks() {
        int totalSize = getTotalSize();
        logger.debug("Expected total size for compression: " + totalSize);
        byte[] result = new byte[totalSize];
        int offset = 0;
        for (DataBlock block : blocks) {
            byte[] bytes = writeDataBlock(block);
            ByteUtils.writeBytes(result, offset, bytes);
            offset += bytes.length;
        }
        return result;
    }

    private byte[] writeDataBlock(DataBlock block) {
        byte[] deflated = deflate(block.decompressed, block);
        byte[] result = new byte[deflated.length + DataBlock.Header.SIZE];
        ByteUtils.debugToFile(deflated, "decompressed_before_magic.bin");
        ByteUtils.writeWord(result, 0x0000, block.header.size);
        ByteUtils.writeWord(result, 0x0002, block.header.decompressedSize);
        ByteUtils.writeDWord(result, 0x0004, block.header.checksum);

        ByteUtils.writeBytes(result, DataBlock.Header.SIZE, deflated);

        ByteUtils.debugToFile(result, "decompressed_before_magic_with_headers.bin");

        result[0x0004] = 0;
        result[0x0004 + 1] = 0;
        result[0x0004 + 2] = 0;
        result[0x0004 + 3] = 0;
        ByteUtils.debugToFile(result, "decompressed_before_magic_with_headers_zeroes_at_crc.bin");

        //Magic
        result[10] = (byte) (result[10] - 1);

        result[result.length - 4] = 0x00;
        result[result.length - 3] = 0x00;
        result[result.length - 2] = (byte) 0xFF;
        if (result[result.length - 1] == 0x00) {
            result[result.length - 5] = 0x00;
        }
        result[result.length - 1] = (byte) 0xFF;
        ByteUtils.debugToFile(result, 0, result.length, "decompressed_after_magic_with_headers_zeroes_at_crc.bin");

        byte[] afterMagic = new byte[deflated.length];
        System.arraycopy(result, 8, afterMagic, 0, afterMagic.length);
        ByteUtils.debugToFile(afterMagic, "decompressed_after_magic.bin");
        return result;
    }

    private byte[] deflate(byte[] decompressed, DataBlock block) {
        byte[] buf = new byte[block.header.size*2];
        Deflater deflater = new Deflater();
        deflater.init(JZlib.Z_BEST_SPEED);
        deflater.setInput(decompressed, 0, decompressed.length, true);
        deflater.setOutput(buf);

        int compressionError;
        while (true) {
            deflater.avail_out = 1;
            compressionError = deflater.deflate(JZlib.Z_FINISH);
            if (compressionError == JZlib.Z_STREAM_END) break;
            DataBlockUtils.checkErrors(deflater, compressionError, "deflate");
        }

//        while (deflater.total_in != decompressed.length &&
//                deflater.total_out < block.header.size) {
//            deflater.avail_in = deflater.avail_out = 1; // force small buffers
//            compressionError = deflater.deflate(JZlib.Z_NO_FLUSH);
//            checkErrors(deflater, compressionError, "deflate");
//        }
//
//        while (true) {
//            deflater.avail_out = 1;
//            try {
//                compressionError = deflater.deflate(JZlib.Z_FINISH);
//                if (compressionError == JZlib.Z_STREAM_END) break;
//                checkErrors(deflater, compressionError, "deflate");
//            } catch (ArrayIndexOutOfBoundsException ai) {
//                logger.error(ai);
//                throw new W3gParserException(ai);
//            }
//        }

        compressionError = deflater.end();
        DataBlockUtils.checkErrors(deflater, compressionError, "deflateEnd");
//        return buf;
        int size = (int)deflater.total_out;
        if (deflater.total_out < block.header.size) {
            size = block.header.size;
        }
        byte[] result = new byte[size];

        System.arraycopy(buf, 0, result, 0, size);
//        if (!Arrays.equals(result, buf)) {
//            logger.debug("Wrong stuff: " + deflater.total_out + " " + buf.length);
//        }
        return result;
    }
}
