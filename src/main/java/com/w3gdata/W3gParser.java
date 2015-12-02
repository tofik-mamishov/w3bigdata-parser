package com.w3gdata;

import com.google.common.io.ByteSource;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.zip.DataFormatException;

public class W3gParser {

    private static final Logger logger = Logger.getLogger(W3gParser.class);

    public W3gInfo parse(File replaySourceFile) throws ParseException, FileNotFoundException {
        W3gInfo result = new W3gInfo();
        FileInputStream fileInputStream = new FileInputStream(replaySourceFile);

        return result;
    }

    public W3gInfo parse(ByteSource replaySource) throws ParseException, IOException, DataFormatException {
        return new W3gByteProcessor().process(replaySource.read());
    }


}
