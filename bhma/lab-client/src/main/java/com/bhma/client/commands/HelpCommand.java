package com.bhma.client.commands;

import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.utility.OutputManager;

import java.util.ArrayList;

/**
 * help command
 */
public class HelpCommand extends Command {
    private final ArrayList<Command> commands;
    private final OutputManager outputManager;

    public HelpCommand(ArrayList<Command> commands, OutputManager outputManager) {
        super("help", "вывести справку по доступным командам");
        this.commands = commands;
        this.outputManager = outputManager;
    }

    /**
     * print all commands with their names and descriptions
     * @param argument must be empty
     * @throws NoSuchCommandException if argument isn't empty
     */
    public void execute(String argument) throws NoSuchCommandException {
        if (!argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        outputManager.println("Список доступных команд:");
        for (Command command : commands) {
            outputManager.println(command.getName() + ": " + command.getDescription());
        }
    }
}
