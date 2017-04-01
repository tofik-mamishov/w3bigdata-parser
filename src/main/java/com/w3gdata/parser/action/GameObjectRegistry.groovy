package com.w3gdata.parser.action

import com.w3gdata.util.ByteReader


enum GameObjectRegistry {
    INSTANCE

    static GameObject read(ByteReader reader) {
        byte[] id = reader.nextBytes(4)
        if (isEmpty(id)) {
            return new Ground()
        }
        if (isAlphanumeric(id)) {
        } else {

        }

    }

    static boolean isAlphanumeric(byte[] id) {
        (id[2] == (byte)0x0D) && (id[3] == (byte)0x00)
    }

    static boolean isEmpty(byte[] id) {
        (id[0] == (byte)0xFF) && (id[1] == (byte)0xFF) &&
        (id[2] == (byte)0xFF) && (id[3] == (byte)0xFF)
    }
}
