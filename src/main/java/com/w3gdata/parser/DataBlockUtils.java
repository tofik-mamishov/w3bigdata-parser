package com.w3gdata.parser;

import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZStream;
import org.apache.log4j.Logger;

public final class DataBlockUtils {

    private static final Logger logger = Logger.getLogger(DataBlockUtils.class);

    private DataBlockUtils() {
    }

    public static void checkErrors(ZStream z, int err, String msg) {
        if (err != JZlib.Z_OK) {
            if (z.msg != null) logger.error(z.msg + " ");
            logger.error(msg + " error: " + err);
            throw new W3gParserException(msg + " error: " + err);
        }
    }

}
