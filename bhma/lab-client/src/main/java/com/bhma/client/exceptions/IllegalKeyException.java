package com.bhma.client.exceptions;

public class IllegalKeyException extends Exception {
    private final String message;

    public IllegalKeyException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
