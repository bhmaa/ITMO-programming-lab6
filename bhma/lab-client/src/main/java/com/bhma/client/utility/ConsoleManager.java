package com.bhma.client.utility;

import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.exceptions.NoConnectionException;
import com.bhma.common.exceptions.ScriptException;
import com.bhma.common.util.ClientRequest;
import com.bhma.common.util.CommandRequirement;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

public class ConsoleManager {
    private final HashMap<String, CommandRequirement> commands;
    private final InputManager inputManager;
    private final OutputManager outputManager;
    private final SpaceMarineFiller spaceMarineFiller;
    private final Requester requester;

    public ConsoleManager(HashMap<String, CommandRequirement> commands, InputManager inputManager,
                          OutputManager outputManager, SpaceMarineFiller spaceMarineFiller, Requester requester) {
        this.commands = commands;
        this.inputManager = inputManager;
        this.outputManager = outputManager;
        this.spaceMarineFiller = spaceMarineFiller;
        this.requester = requester;
    }

    /**
     * starts read commands and execute it while it is not an exit command
     */
    public void start() throws IOException, ClassNotFoundException, InvalidInputException, NoConnectionException {
        boolean executeFlag = true;
        while (executeFlag) {
            outputManager.print(">");
            String input = inputManager.read();
            if (!input.trim().isEmpty()) {
                String inputCommand = input.split(" ")[0].toLowerCase(Locale.ROOT);
                String argument = "";
                if (input.split(" ").length > 1) {
                    argument = input.replaceFirst(inputCommand + " ", "");
                }
                try {
                    ClientRequest request = new ClientRequest(inputCommand, argument, getObjectArgument(inputCommand));
                    ServerResponse response = requester.send(request);
                    executeFlag = processServerResponse(response);
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

    public Object getObjectArgument(String commandName) throws ScriptException, InvalidInputException {
        Object object = null;
        if (commands.containsKey(commandName)) {
            CommandRequirement requirement = commands.get(commandName);
            switch (requirement) {
                case CHAPTER:
                    object = spaceMarineFiller.fillChapter();
                    break;
                case SPACE_MARINE:
                    object = spaceMarineFiller.fillSpaceMarine();
                    break;
                case WEAPON:
                    object = spaceMarineFiller.fillWeaponType();
                    break;
                default:
                    break;
            }
        }
        return object;
    }

    /**
     * process the ExecuteCode of ServerResponse. print messages and finish read script if there's an error
     * @param serverResponse received response
     * @return false if it was exit command, true otherwise
     */
    public boolean processServerResponse(ServerResponse serverResponse) {
        ExecuteCode executeCode = serverResponse.getExecuteCode();
        switch (executeCode) {
            case ERROR:
                inputManager.finishReadScript();
                outputManager.printlnColorMessage(executeCode.getMessage(), Color.RED);
                outputManager.printlnColorMessage(serverResponse.getMessage(), Color.RED);
                break;
            case SUCCESS:
                outputManager.printlnColorMessage(executeCode.getMessage(), Color.GREEN);
                break;
            case VALUE:
                outputManager.printlnImportantMessage(executeCode.getMessage());
                outputManager.printlnImportantMessage(serverResponse.getMessage());
                break;
            case READ_SCRIPT:
                inputManager.startReadScript(serverResponse.getMessage());
                break;
            case EXIT:
                outputManager.printlnImportantColorMessage(executeCode.getMessage(), Color.RED);
                return false;
            default:
                outputManager.printlnImportantColorMessage("incorrect server's response...", Color.RED);
        }
        return true;
    }
}
