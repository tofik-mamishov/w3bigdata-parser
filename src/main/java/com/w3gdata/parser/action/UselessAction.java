package com.w3gdata.parser.action;

import com.w3gdata.util.ByteBuffer;

public class UselessAction implements Action {

    private final int id;
    private final int size;

    public UselessAction(int id, int size) {
        this.id = id;
        this.size = size;
    }

    public UselessAction skip(ByteBuffer buf) {
        buf.increment(getSize());
        return this;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Action deserialize(ByteBuffer buf) {
        return skip(buf);
    }

    public int getSize() {
        return size;
    }

    public static class Builder {
        private int id;
        private int skip;

        private Builder() {
        }

        public static Builder anUselessAction() {
            return new Builder();
        }

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withSkip(int skip) {
            this.skip = skip;
            return this;
        }

        public Builder but() {
            return anUselessAction().withId(id).withSkip(skip);
        }

        public UselessAction build() {
            return new UselessAction(id, skip);
        }
    }
}
