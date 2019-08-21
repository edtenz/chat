package com.tenchael.chat.exceptions;

public class ProcessException extends RuntimeException {

    public ProcessException() {
        super();
    }

    public ProcessException(String message) {
        super(message);
    }

    public ProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessException(Throwable cause) {
        super(cause);
    }

    protected ProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
