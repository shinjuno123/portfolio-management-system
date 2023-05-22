package com.amazing.juno.springwebapp.exc;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Check your ids")
public class NotSavedException extends RuntimeException{
    public NotSavedException() {
        super();
    }

    public NotSavedException(String message) {
        super(message);
    }

    public NotSavedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSavedException(Throwable cause) {
        super(cause);
    }

    protected NotSavedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
