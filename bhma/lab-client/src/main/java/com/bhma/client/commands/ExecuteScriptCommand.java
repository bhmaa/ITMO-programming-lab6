package com.bhma.client.commands;

import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.utility.InputManager;

/**
 * execute_script command
 */
public class ExecuteScriptCommand extends Command {
    private InputManager inputManager;

    public ExecuteScriptCommand(InputManager inputManager) {
        super("execute_script", "считать и исполнить скрипт из указанного файла");
        this.inputManager = inputManager;
    }

    /**
     * read and execute script from entered file
     * @param argument mustn't be empty
     * @throws NoSuchCommandException if argument is empty
     */
    public void execute(String argument) throws NoSuchCommandException {
        if (argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        inputManager.startReadScript(argument);
    }
}
