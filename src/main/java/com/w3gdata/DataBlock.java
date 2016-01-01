package com.w3gdata;

import com.google.common.base.Objects;

import java.util.Arrays;

public class DataBlock {

    public static class Header {
        public static final int SIZE = 0x8;
        public int size;
        public int decompressedSize;
        public int checksum;

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Header other = (Header) obj;
            return Objects.equal(this.size, other.size)
                    && Objects.equal(this.decompressedSize, other.decompressedSize)
                    && Objects.equal(this.checksum, other.checksum);
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getDecompressedSize() {
            return decompressedSize;
        }

        public void setDecompressedSize(int decompressedSize) {
            this.decompressedSize = decompressedSize;
        }

        public int getChecksum() {
            return checksum;
        }

        public void setChecksum(int checksum) {
            this.checksum = checksum;
        }
    }

    public Header header = new Header();
    public byte[] decompressed;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataBlock other = (DataBlock) obj;
        return Objects.equal(this.header, other.header)
                && Arrays.equals(this.decompressed, this.decompressed);
    }

    public Header getHeader() {
        return header;
    }

    public byte[] getDecompressed() {
        return decompressed;
    }
}
