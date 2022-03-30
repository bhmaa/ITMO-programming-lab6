package com.bhma.server.util;

import com.bhma.common.exceptions.IllegalKeyException;
import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.exceptions.ScriptException;
import com.bhma.common.util.ClientRequest;
import com.bhma.common.util.Color;
import com.bhma.server.commands.Command;

import java.io.IOException;
import java.util.Optional;

public class ExecuteManager {
    private final CommandManager commandManager;
    private final OutputManager outputManager;
    private final InputManager inputManager;

    public ExecuteManager(CommandManager commandManager, OutputManager outputManager, InputManager inputManager) {
        this.commandManager = commandManager;
        this.outputManager = outputManager;
        this.inputManager = inputManager;
    }

    public void start() throws IOException, ClassNotFoundException, InvalidInputException {
        boolean executeFlag = true;
        while (executeFlag) {
            ClientRequest clientRequest = Sender.receiveRequest();
            String inputCommand = clientRequest.getCommandName();
            String argument = clientRequest.getCommandArguments();
            Optional<Command> optional = commandManager.getCommands().stream()
                    .filter(v -> v.getName().equals(inputCommand)).findFirst();
                if (optional.isPresent()) {
                    try {
                        Command command = optional.get();
                        command.execute(argument);
                        executeFlag = command.getExecuteFlag();
                        outputManager.printlnColorMessage("The command completed", Color.GREEN);
                    } catch (ScriptException | InvalidCommandArguments | IllegalKeyException e) {
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
                                + " their names and descriptions", Color.RED);
                    }
                }
            }
        }
}
