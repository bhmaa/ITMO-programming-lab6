package com.bhma.client.commands;

import com.bhma.client.exceptions.IllegalKeyException;
import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.exceptions.ScriptException;

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

    public abstract void execute(String argument) throws NoSuchCommandException, ScriptException, IllegalKeyException;

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
