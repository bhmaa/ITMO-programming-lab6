package com.bhma.client.utility;

import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.exceptions.ScriptException;
import com.bhma.common.util.ClientRequest;
import com.bhma.common.util.Color;
import com.bhma.common.util.CommandRequirement;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerRequest;
import com.bhma.common.util.ServerResponse;

import java.io.IOException;
import java.util.Locale;

public class ConsoleManager {
    private final InputManager inputManager;
    private final OutputManager outputManager;
    private final SpaceMarineFiller spaceMarineFiller;

    public ConsoleManager(InputManager inputManager, OutputManager outputManager, SpaceMarineFiller spaceMarineFiller) {
        this.inputManager = inputManager;
        this.outputManager = outputManager;
        this.spaceMarineFiller = spaceMarineFiller;
    }

    /**
     * starts read commands and execute it while it is not an exit command
     */
    public void start() throws IOException, ClassNotFoundException, InvalidInputException {
        while (true) {
            String input = inputManager.read();
            if (!input.trim().isEmpty()) {
                String inputCommand = input.split(" ")[0].toLowerCase(Locale.ROOT);
                String argument = "";
                if (input.split(" ").length > 1) {
                    argument = input.replaceFirst(inputCommand + " ", "");
                }
                Sender.send(new ClientRequest(inputCommand, argument));
                Object answer = Sender.receiveObject();
                if (answer instanceof ServerRequest) {
                    CommandRequirement requirement = ((ServerRequest) answer).getCommandRequirement();
                    try {
                        if (requirement.equals(CommandRequirement.CHAPTER)) {
                            Sender.send(spaceMarineFiller.fillChapter());
                        }
                        if (requirement.equals(CommandRequirement.SPACE_MARINE)) {
                            Sender.send(spaceMarineFiller.fillSpaceMarine());
                        }
                        if (requirement.equals(CommandRequirement.WEAPON)) {
                            Sender.send(spaceMarineFiller.fillWeaponType());
                        }
                    } catch (ScriptException e) {
                        inputManager.finishReadScript();
                        outputManager.printlnImportantColorMessage(e.getMessage(), Color.RED);
                    }
                    answer = Sender.receiveObject();
                }
                ServerResponse serverResponse = (ServerResponse) answer;
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
                    outputManager.println(executeCode.getMessage());
                    outputManager.println(serverResponse.getMessage());
                }
                if (executeCode.equals(ExecuteCode.READ_SCRIPT)) {
                    outputManager.println(executeCode.getMessage());
                    inputManager.startReadScript(serverResponse.getMessage());
                }
                if (executeCode.equals(ExecuteCode.EXIT)) {
                    outputManager.printlnImportantColorMessage(executeCode.getMessage(), Color.RED);
                    break;
                }
            } else {
                outputManager.printlnColorMessage("Please type any command. To see list of command type \"help\"",
                        Color.RED);
            }
        }
    }
}
