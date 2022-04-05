package com.bhma.common.exceptions;

public class IllegalPortException extends Exception {
    private final String message;

    public IllegalPortException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
