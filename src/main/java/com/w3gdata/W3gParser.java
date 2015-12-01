package com.w3gdata;

import com.google.common.io.ByteSource;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class W3gParser {

    private static final Logger logger = Logger.getLogger(W3gParser.class);

    public StatisticsData parse(File replaySourceFile) throws ParseException, FileNotFoundException {
        StatisticsData result = new StatisticsData();
        FileInputStream fileInputStream = new FileInputStream(replaySourceFile);

        return result;
    }

    public StatisticsData parse(ByteSource replaySource) throws ParseException, IOException {
        return replaySource.read(new StatisticsByteProcessor());
    }


}
