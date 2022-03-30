package com.bhma.server.commands;

import com.bhma.common.exceptions.IllegalKeyException;
import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.exceptions.ScriptException;

import java.io.IOException;

/**
 * parent of all commands
 */
public abstract class Command {
    private final String name;
    private final String description;
    private boolean executeFlag = true;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract void execute(String argument) throws InvalidCommandArguments,
            ScriptException, IllegalKeyException, InvalidInputException, IOException, ClassNotFoundException;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getExecuteFlag() {
        return executeFlag;
    }

    /**
     * @param executeFlag false if it is an exit command and true if it is not
     */
    public void setExecuteFlag(boolean executeFlag) {
        this.executeFlag = executeFlag;
    }
}
