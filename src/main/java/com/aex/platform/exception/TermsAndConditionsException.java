package com.aex.platform.exception;

/**
 * Exception thrown when the user has not accepted the terms and conditions
 */
public class TermsAndConditionsException extends RuntimeException {
    public TermsAndConditionsException(String message) {
        super(message);
    }

}
