package com.w3gdata.parser.util;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import com.w3gdata.parser.EncodedStringDecoder;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;

public class EncodedStringDecoderTest {

    public static final String ENCODED_STRING_FILE_NAME = "encoded_string.bin";

    public static final String EXPECTED_ENCODED_STRING = "Maps\\w3arena\\w3arena__turtlerock__v3.w3x";

    public static final int DECODED_MAP_FROM = 13;

    public static final int DECODED_MAP_TO = 53;

    private ByteSource encodedByteSource;

    private EncodedStringDecoder encodedStringDecoder;

    @Before
    public void setUp() throws Exception {
        URL resourceURL = Resources.getResource(ENCODED_STRING_FILE_NAME);
        encodedByteSource = Resources.asByteSource(resourceURL);
        encodedStringDecoder = new EncodedStringDecoder();
    }

    @Test
    public void testDecode() throws Exception {
        String decodedString = encodedStringDecoder.decode(new String(encodedByteSource.read()));
        assertEquals(EXPECTED_ENCODED_STRING, decodedString.substring(DECODED_MAP_FROM, DECODED_MAP_TO));
    }
}