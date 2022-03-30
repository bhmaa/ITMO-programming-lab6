package com.bhma.common.util;

import java.io.Serializable;

public class ClientRequest implements Serializable {
    private final String commandName;
    private final String commandArguments;

    public ClientRequest(String commandName, String commandArguments) {
        this.commandName = commandName;
        this.commandArguments = commandArguments;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getCommandArguments() {
        return commandArguments;
    }
}
