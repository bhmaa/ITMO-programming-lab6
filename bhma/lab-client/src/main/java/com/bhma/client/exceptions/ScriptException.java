package com.bhma.client.exceptions;

public class ScriptException extends Exception {
    private final String message = "Error during script execution";

    public String getMessage() {
        return message;
    }
}
