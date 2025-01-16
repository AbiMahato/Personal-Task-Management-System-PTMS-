package com.ptms.ptms.exception;

import org.springframework.http.HttpStatus;

public class PtmsException {
    private final String message;
    private final Throwable throwable;
    private final HttpStatus status;

    public PtmsException(String message, Throwable throwable, HttpStatus status) {
        this.message = message;
        this.throwable = throwable;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
