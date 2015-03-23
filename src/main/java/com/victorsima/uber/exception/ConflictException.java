package com.victorsima.uber.exception;

/**
 * An obj representing a HTTP 409 Conflict error used for surge confirmations
 */
public class ConflictException extends Exception {

    public ConflictException() {
        super("HTTP 403 error");
    }

}
