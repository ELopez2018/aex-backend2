package com.aex.platform.exception;

/**
 * ObjectNotFoundException is a custom exception for when an object is not found.
 */
public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException() {}

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
