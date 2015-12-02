package com.w3gdata;

public class ProcessorException extends RuntimeException {
    public ProcessorException(Throwable cause) {
        super(cause);
    }

    public ProcessorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessorException(String message) {
        super(message);
    }
}
