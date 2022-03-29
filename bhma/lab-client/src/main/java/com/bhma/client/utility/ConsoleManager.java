package com.bhma.client.utility;

import com.bhma.client.commands.Command;
import com.bhma.client.exceptions.IllegalKeyException;
import com.bhma.client.exceptions.InvalidInputException;
import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.exceptions.ScriptException;

import java.util.Locale;
import java.util.Optional;

public class ConsoleManager {
    private final CommandManager commandManager;
    private final InputManager inputManager;
    private final OutputManager outputManager;

    public ConsoleManager(CommandManager commandManager, InputManager inputManager, OutputManager outputManager) {
        this.commandManager = commandManager;
        this.inputManager = inputManager;
        this.outputManager = outputManager;
    }

    /**
     * starts read commands and execute it while it is not an exit command
     */
    public void start() throws InvalidInputException {
        boolean executeFlag = true;
        while (executeFlag) {
            String input = inputManager.read();
            if (!input.trim().isEmpty()) {
                String inputCommand = input.split(" ")[0].toLowerCase(Locale.ROOT);
                String argument = "";
                if (input.split(" ").length > 1) {
                    argument = input.replaceFirst(inputCommand + " ", "");
                }
                Optional<Command> optional = commandManager.getCommands().stream().filter(v -> v.getName().equals(inputCommand)).findFirst();
                if (optional.isPresent()) {
                    try {
                        Command command = optional.get();
                        command.execute(argument);
                        executeFlag = command.getExecuteFlag();
                        outputManager.printlnColorMessage("The command completed", Color.GREEN);
                    } catch (ScriptException | NoSuchCommandException | IllegalKeyException e) {
                        inputManager.finishReadScript();
                        outputManager.printlnImportantColorMessage(e.getMessage(), Color.RED);
                    } catch (NumberFormatException e) {
                        inputManager.finishReadScript();
                        outputManager.printlnImportantColorMessage("Wrong number format", Color.RED);
                    }
                } else {
                    if (inputManager.getScriptMode()) {
                        inputManager.finishReadScript();
                        outputManager.printlnImportantColorMessage("Unknown command detected: " + inputCommand,
                                Color.RED);
                    } else {
                        outputManager.printlnColorMessage("No such command. Type \"help\" to get all commands with"
                                + "their names and descriptions", Color.RED);
                    }
                }
            } else {
                outputManager.printlnColorMessage("Please type any command. To see list of command type \"help\"",
                        Color.RED);
            }
        }
    }
}
