package com.bhma.common.util;

import java.io.Serializable;

public class ServerResponse implements Serializable {
    private final String message;
    private final ExecuteCode executeCode;

    public ServerResponse(String message, ExecuteCode executeCode) {
        this.message = message;
        this.executeCode = executeCode;
    }

    public ServerResponse(ExecuteCode executeCode) {
        this.message = "";
        this.executeCode = executeCode;
    }

    public String getMessage() {
        return message;
    }

    public ExecuteCode getExecuteCode() {
        return executeCode;
    }
}
