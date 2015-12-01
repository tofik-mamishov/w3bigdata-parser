package com.w3gdata;

public class DataBlock {

    public static class Header {
        public static final int SIZE = 0x8;
        public int size;
        public int decompressedSize;
    }

    public Header header = new Header();
    public byte[] decompressed;
}
