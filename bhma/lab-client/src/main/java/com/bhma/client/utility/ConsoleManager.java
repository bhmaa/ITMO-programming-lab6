package com.bhma.client.utility;

import com.bhma.client.commands.Command;
import com.bhma.client.exceptions.IllegalKeyException;
import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.exceptions.ScriptException;

import java.util.Locale;

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
    public void start() {
        boolean executeFlag = true;
        while (executeFlag) {
            String input = inputManager.read();
            String inputCommand = input.split(" ")[0];
            String argument = "";
            boolean completed = false;
            if (input.split(" ").length > 1) {
                argument = input.replaceFirst(inputCommand + " ", "");
            }
            for (Command command : commandManager.getCommands()) {
                if (command.getName().equals(inputCommand.toLowerCase(Locale.ROOT))) {
                    try {
                        completed = true;
                        command.execute(argument);
                        executeFlag = command.getExecuteFlag();
                        outputManager.println("The command completed");
                        break;
                    } catch (ScriptException e) {
                        inputManager.finishReadScript();
                        outputManager.println(e.getMessage());
                    } catch (NoSuchCommandException | IllegalKeyException e) {
                        if (inputManager.getScriptMode()) {
                            inputManager.finishReadScript();
                        }
                        outputManager.println(e.getMessage());
                    } catch (NumberFormatException e) {
                        if (inputManager.getScriptMode()) {
                            inputManager.finishReadScript();
                        }
                        outputManager.println("Wrong number format");
                    }
                }
            }
            if (!completed) {
                outputManager.println("No such command. Type \"help\" to get all commands with their names and descriptions");
            }
        }
    }
}
