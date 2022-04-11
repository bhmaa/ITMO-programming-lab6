package com.bhma.server.commands;

import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringJoiner;

/**
 * help command
 */
public class HelpCommand extends Command {
    private final HashMap<String, Command> commands;

    public HelpCommand(HashMap<String, Command> commands) {
        super("help", "вывести справку по доступным командам");
        this.commands = commands;
    }

    /**
     * print all commands with their names and descriptions
     * @param argument must be empty
     * @throws InvalidCommandArguments if argument isn't empty
     */
    public ServerResponse execute(String argument, Object object) throws InvalidCommandArguments, IOException {
        if (!argument.isEmpty() || object != null) {
            throw new InvalidCommandArguments();
        }
        StringJoiner message = new StringJoiner("\n");
        message.add("Список доступных команд:");
        commands.forEach((k, v) -> message.add(k + ": " + v.getDescription()));
        return new ServerResponse(message.toString(), ExecuteCode.VALUE);
    }
}
