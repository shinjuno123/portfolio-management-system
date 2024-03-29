package com.amazing.juno.pmsrest.exc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "You entered bad credentials")
public class CustomBadCredentialsException extends RuntimeException{

    public CustomBadCredentialsException() {
        super();
    }

    public CustomBadCredentialsException(String message) {
        super(message);
    }

    public CustomBadCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomBadCredentialsException(Throwable cause) {
        super(cause);
    }

    protected CustomBadCredentialsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
