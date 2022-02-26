package com.bhma.client.exceptions;

public class ScriptException extends Exception {

    public String getMessage() {
        return "Error during script execution";
    }
}
