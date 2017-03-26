package com.w3gdata.parser;

import com.google.common.primitives.Bytes;
import com.jcraft.jzlib.Inflater;
import com.jcraft.jzlib.JZlib;
import com.w3gdata.util.ByteReader;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import static com.w3gdata.parser.DataBlockUtils.checkErrors;

public class DataBlockReader {

    private static final Logger logger = Logger.getLogger(DataBlockReader.class);

    private final ByteReader reader;

    public DataBlockReader(ByteReader reader) {
        this.reader = reader;
    }

    public byte[] nextDataBlocks() {
        try {
            final List<DataBlock> blocks = new ArrayList<>();
            while (reader.hasMore()) {
                DataBlock block = nextDataBlock();
                blocks.add(block);
            }
            return concatenate(blocks);
        } catch (DataFormatException e) {
            throw new W3gParserException(e.getMessage(), e);
        }
    }

    private DataBlock nextDataBlock() throws DataFormatException {
        DataBlockHeader header = new DataBlockHeader(reader);
        DataBlock block = new DataBlock(header);
        inflate(block);
        reader.forward(block.header.size);
        return block;
    }

    private void inflate(DataBlock block) {
        Inflater inflater = new Inflater();
        inflater.setInput(reader.getBuf(), reader.offset(), block.header.size, true);
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
        byte[][] concatenated = new byte[blocks.size()][];
        for (int i = 0; i < blocks.size(); i++) {
            concatenated[i] = blocks.get(i).decompressed;
        }
        return Bytes.concat(concatenated);
    }
}
