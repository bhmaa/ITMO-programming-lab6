package com.bhma.server.util;

import com.bhma.common.exceptions.IllegalKeyException;
import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.exceptions.ScriptException;
import com.bhma.common.util.ClientRequest;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.commands.Command;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.util.Optional;

public class ExecuteManager {
    private final CommandManager commandManager;
    private final DatagramChannel channel;

    public ExecuteManager(CommandManager commandManager, DatagramChannel channel) {
        this.commandManager = commandManager;
        this.channel = channel;
    }

    public void start() throws IOException, ClassNotFoundException, InvalidInputException {
        boolean executeFlag = true;
        while (executeFlag) {
            ClientRequest clientRequest = Sender.receiveRequest(channel);
            String inputCommand = clientRequest.getCommandName();
            String argument = clientRequest.getCommandArguments();
            Optional<Command> optional = commandManager.getCommands().stream()
                    .filter(v -> v.getName().equals(inputCommand)).findFirst();
            if (optional.isPresent()) {
                try {
                    Command command = optional.get();
                    command.execute(argument);
                    executeFlag = command.getExecuteFlag();
                } catch (ScriptException | InvalidCommandArguments | IllegalKeyException e) {
                    Sender.send(channel, new ServerResponse(e.getMessage(), ExecuteCode.ERROR));
                } catch (NumberFormatException e) {
                    Sender.send(channel, new ServerResponse("Wrong number format", ExecuteCode.ERROR));
                }
            } else {
                Sender.send(channel, new ServerResponse("Unknown command detected: " + inputCommand,
                        ExecuteCode.ERROR));
            }
        }
    }
}
