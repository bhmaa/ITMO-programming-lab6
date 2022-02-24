package com.bhma.client.exceptions;

public class NoSuchCommandException extends Exception {
    private final String message = "Command with wrong argument. Type \"help\" to get all commands with their name and description";

    public String getMessage() {
        return message;
    }
}
