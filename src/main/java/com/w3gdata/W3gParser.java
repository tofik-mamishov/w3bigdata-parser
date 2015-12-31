package com.w3gdata;

import com.google.common.io.ByteSource;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.util.zip.DataFormatException;

public class W3gParser {

    private static final Logger logger = Logger.getLogger(W3gParser.class);

    W3gByteProcessor processor;

    public W3gParser() {
        processor = new W3gByteProcessor();
    }

    public W3gInfo parse(ByteSource replaySource) throws ParseException, IOException, DataFormatException {
        return processor.process(replaySource.read());
    }
}
