package com.w3gdata;

public class W3gParserException extends RuntimeException {
    public W3gParserException(Throwable cause) {
        super(cause);
    }

    public W3gParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public W3gParserException(String message) {
        super(message);
    }
}
