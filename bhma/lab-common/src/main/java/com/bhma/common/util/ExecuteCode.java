package com.bhma.common.util;

import java.io.Serializable;

public enum ExecuteCode implements Serializable {
    SUCCESS("The command was completed"),
    ERROR("The command was NOT completed!"),
    VALUE("Server response:"),
    EXIT("Disconnecting with server..."),
    READ_SCRIPT("Start reading script...");

    private final String message;

    ExecuteCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
