package com.bhma.client.utility;

import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.exceptions.ScriptException;
import com.bhma.common.util.ClientRequest;
import com.bhma.common.util.CommandRequirement;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Locale;

public class ConsoleManager {
    private HashMap<String, CommandRequirement> commands;
    private final InputManager inputManager;
    private final OutputManager outputManager;
    private final SpaceMarineFiller spaceMarineFiller;

    public ConsoleManager(HashMap<String, CommandRequirement> commands, InputManager inputManager, OutputManager outputManager,
                          SpaceMarineFiller spaceMarineFiller) {
        this.commands = commands;
        this.inputManager = inputManager;
        this.outputManager = outputManager;
        this.spaceMarineFiller = spaceMarineFiller;
    }

    /**
     * starts read commands and execute it while it is not an exit command
     */
    public void start() throws IOException, ClassNotFoundException, InvalidInputException {
        boolean executeFlag = true;
        while (executeFlag) {
            outputManager.println("Forming a request. Type a command");
            String input = inputManager.read();
            if (!input.trim().isEmpty()) {
                String inputCommand = input.split(" ")[0].toLowerCase(Locale.ROOT);
                String argument = "";
                if (input.split(" ").length > 1) {
                    argument = input.replaceFirst(inputCommand + " ", "");
                }
                try {
                    ClientRequest request = new ClientRequest(inputCommand, argument, checkObjectArgument(inputCommand));
                    final int totalAttempts = 5;
                    for (int attempt = 1; attempt <= totalAttempts; attempt++) {
                        try {
                            ServerResponse response = Requester.send(request);
                            executeFlag = processServerResponse(response);
                            break;
                        } catch (SocketTimeoutException e) {
                            outputManager.printlnImportantColorMessage("Cannot connect with server. Retrying attempt #"
                                    + attempt + " now...", Color.RED);
                            if (attempt == totalAttempts) {
                                throw new SocketTimeoutException();
                            }
                        }
                    }
                } catch (ScriptException e) {
                    inputManager.finishReadScript();
                    outputManager.printlnImportantColorMessage(e.getMessage(), Color.RED);
                }
            } else {
                outputManager.printlnColorMessage("Please type any command. To see list of command type \"help\"",
                        Color.RED);
            }
        }
    }

    public Object checkObjectArgument(String commandName) throws ScriptException, InvalidInputException {
        Object object = null;
        if (commands.containsKey(commandName)) {
            CommandRequirement requirement = commands.get(commandName);
            if (requirement.equals(CommandRequirement.CHAPTER)) {
                object = spaceMarineFiller.fillChapter();
            }
            if (requirement.equals(CommandRequirement.SPACE_MARINE)) {
                object = spaceMarineFiller.fillSpaceMarine();
            }
            if (requirement.equals(CommandRequirement.WEAPON)) {
                object = spaceMarineFiller.fillWeaponType();
            }
        }
        return object;
    }

    public boolean processServerResponse(ServerResponse serverResponse) {
        ExecuteCode executeCode = serverResponse.getExecuteCode();
        if (executeCode.equals(ExecuteCode.ERROR)) {
            inputManager.finishReadScript();
            outputManager.printlnColorMessage(executeCode.getMessage(), Color.RED);
            outputManager.printlnColorMessage(serverResponse.getMessage(), Color.RED);
        }
        if (executeCode.equals(ExecuteCode.SUCCESS)) {
            outputManager.printlnColorMessage(executeCode.getMessage(), Color.GREEN);
        }
        if (executeCode.equals(ExecuteCode.VALUE)) {
            outputManager.printlnImportantMessage(executeCode.getMessage());
            outputManager.printlnImportantMessage(serverResponse.getMessage());
        }
        if (executeCode.equals(ExecuteCode.READ_SCRIPT)) {
            inputManager.startReadScript(serverResponse.getMessage());
        }
        if (executeCode.equals(ExecuteCode.EXIT)) {
            outputManager.printlnImportantColorMessage(executeCode.getMessage(), Color.RED);
            return false;
        }
        return true;
    }
}
