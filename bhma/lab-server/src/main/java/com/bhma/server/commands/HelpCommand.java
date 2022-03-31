package com.bhma.server.commands;

import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.Sender;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * help command
 */
public class HelpCommand extends Command {
    private final ArrayList<Command> commands;
    private final DatagramChannel channel;

    public HelpCommand(ArrayList<Command> commands, DatagramChannel channel) {
        super("help", "вывести справку по доступным командам");
        this.commands = commands;
        this.channel = channel;
    }

    /**
     * print all commands with their names and descriptions
     * @param argument must be empty
     * @throws InvalidCommandArguments if argument isn't empty
     */
    public void execute(String argument) throws InvalidCommandArguments, IOException {
        if (!argument.isEmpty()) {
            throw new InvalidCommandArguments();
        }
        StringJoiner message = new StringJoiner("\n");
        message.add("Список доступных команд:");
        for (Command command : commands) {
            message.add(command.getName() + ": " + command.getDescription());
        }
        Sender.send(channel, new ServerResponse(message.toString(), ExecuteCode.VALUE));
    }
}
