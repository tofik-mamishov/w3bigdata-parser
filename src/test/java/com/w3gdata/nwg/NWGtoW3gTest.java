package com.w3gdata.nwg;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import com.google.common.primitives.Bytes;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.FileOutputStream;
import java.net.URL;

public class NWGtoW3gTest {

    private static final Logger logger = Logger.getLogger(NWGtoW3gTest.class);

    @Test
    public void convertNwgTow3G() throws Exception {
        String name = "long";
        URL resourceURL = Resources.getResource(name + ".nwg");
        ByteSource replaySourceFile = Resources.asByteSource(resourceURL);
        byte[] bytes = replaySourceFile.read();
        String label = "Warcraft III recorded game";
        int offset = Bytes.indexOf(bytes, label.getBytes());
        FileOutputStream fos = new FileOutputStream(name + ".w3g");
        fos.write(bytes, offset, bytes.length - offset);
    }
}
