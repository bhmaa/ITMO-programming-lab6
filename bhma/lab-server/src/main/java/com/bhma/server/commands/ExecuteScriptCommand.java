package com.bhma.server.commands;

import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.Sender;

import java.io.IOException;
import java.nio.channels.DatagramChannel;

/**
 * execute_script command
 */
public class ExecuteScriptCommand extends Command {
    private final DatagramChannel channel;

    public ExecuteScriptCommand(DatagramChannel channel) {
        super("execute_script", "считать и исполнить скрипт из указанного файла");
        this.channel = channel;
    }

    /**
     * switches input manager to a script mode
     * @param argument mustn't be empty
     * @throws InvalidCommandArguments if argument is empty
     */
    public void execute(String argument) throws InvalidCommandArguments, IOException {
        if (argument.isEmpty()) {
            throw new InvalidCommandArguments();
        }
        Sender.send(channel, new ServerResponse(argument, ExecuteCode.READ_SCRIPT));
    }
}
