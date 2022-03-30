package com.bhma.common.util;

import java.io.Serializable;

public class ServerRequest implements Serializable {
    private final String message;
    private final CommandRequirement commandRequirement;

    public ServerRequest(String message, CommandRequirement commandRequirement) {
        this.message = message;
        this.commandRequirement = commandRequirement;
    }

    public String getMessage() {
        return message;
    }

    public CommandRequirement getCommandRequirement() {
        return commandRequirement;
    }
}
