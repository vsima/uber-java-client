package com.victorsima.uber.exception;

/**
 * An obj representing a HTTP 403 Forbidden error
 */
public class ForbiddenException extends Exception {

    public ForbiddenException() {
        super("HTTP 403 error");
    }
}
