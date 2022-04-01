package com.bhma.common.util;

import java.io.Serializable;

public class ClientRequest implements Serializable {
    private final String commandName;
    private final String commandArguments;
    private final Object objectArgument;

    public ClientRequest(String commandName, String commandArguments, Object objectArgument) {
        this.commandName = commandName;
        this.commandArguments = commandArguments;
        this.objectArgument = objectArgument;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getCommandArguments() {
        return commandArguments;
    }

    public Object getObjectArgument() {
        return objectArgument;
    }

    @Override
    public String toString() {
        return "ClientRequest{"
                + " commandName='" + commandName + '\''
                + ", commandArguments='" + commandArguments + '\''
                + ", objectArgument=" + objectArgument
                + '}';
    }
}
