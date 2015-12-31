package com.w3gdata;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import org.apache.log4j.Logger;

import java.net.URL;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public abstract class ParsingTest {

    private static final Logger logger = Logger.getLogger(ParsingTest.class);

    protected W3gInfo w3gInfo;
    protected W3gParser parser;

    public ParsingTest(String name) {
        URL resourceURL = Resources.getResource(name);
        ByteSource replaySourceFile = Resources.asByteSource(resourceURL);
        parser = new W3gParser();
        try {
            w3gInfo = parser.parse(replaySourceFile);
        } catch (Exception e) {
            fail("Couldn't parse " + name);
        }
        assertNotNull(w3gInfo);
    }
}
