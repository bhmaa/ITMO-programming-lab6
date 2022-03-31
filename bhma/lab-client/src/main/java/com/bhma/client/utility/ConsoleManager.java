package com.bhma.client.utility;

import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.exceptions.ScriptException;
import com.bhma.common.util.ClientRequest;
import com.bhma.common.util.CommandRequirement;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerRequest;
import com.bhma.common.util.ServerResponse;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.util.Locale;

public class ConsoleManager {
    private final InputManager inputManager;
    private final OutputManager outputManager;
    private final SpaceMarineFiller spaceMarineFiller;
    private final DatagramChannel channel;

    public ConsoleManager(InputManager inputManager, OutputManager outputManager, SpaceMarineFiller spaceMarineFiller,
                          DatagramChannel channel) {
        this.inputManager = inputManager;
        this.outputManager = outputManager;
        this.spaceMarineFiller = spaceMarineFiller;
        this.channel = channel;
    }

    /**
     * starts read commands and execute it while it is not an exit command
     */
    public void start() throws IOException, ClassNotFoundException, InvalidInputException {
        boolean executeFlag = true;
        while (executeFlag) {
            String input = inputManager.read();
            if (!input.trim().isEmpty()) {
                String inputCommand = input.split(" ")[0].toLowerCase(Locale.ROOT);
                String argument = "";
                if (input.split(" ").length > 1) {
                    argument = input.replaceFirst(inputCommand + " ", "");
                }
                Sender.send(channel, new ClientRequest(inputCommand, argument));
                Object answer = Sender.receiveObject(channel);
                if (answer instanceof ServerRequest) {
                    processServerRequest((ServerRequest) answer);
                    answer = Sender.receiveResponse(channel);
                }
                executeFlag = processServerResponse((ServerResponse) answer);
            } else {
                outputManager.printlnColorMessage("Please type any command. To see list of command type \"help\"",
                        Color.RED);
            }
        }
    }

    public void processServerRequest(ServerRequest serverRequest) throws IOException {
        CommandRequirement requirement = ((ServerRequest) serverRequest).getCommandRequirement();
        try {
            if (requirement.equals(CommandRequirement.CHAPTER)) {
                Sender.send(channel, spaceMarineFiller.fillChapter());
            }
            if (requirement.equals(CommandRequirement.SPACE_MARINE)) {
                Sender.send(channel, spaceMarineFiller.fillSpaceMarine());
            }
            if (requirement.equals(CommandRequirement.WEAPON)) {
                Sender.send(channel, spaceMarineFiller.fillWeaponType());
            }
        } catch (ScriptException | InvalidInputException e) {
            inputManager.finishReadScript();
            outputManager.printlnImportantColorMessage(e.getMessage(), Color.RED);
        }
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
