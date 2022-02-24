package com.bhma.client.commands;

import com.bhma.client.exceptions.NoSuchCommandException;

/**
 * exit command
 */
public class ExitCommand extends Command {
    public ExitCommand() {
        super("exit", "завершить программу (без сохранения в файл)");
    }

    /**
     * sets execute flag (false).
     * @param argument must be empty
     * @throws NoSuchCommandException if argument isn't empty
     */
    public void execute(String argument) throws NoSuchCommandException {
        if (!argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        setExecuteFlag(false);
    }
}
