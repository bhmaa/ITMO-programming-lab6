package com.bhma.server.commands;

import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;
import com.bhma.server.util.Sender;

import java.io.IOException;

/**
 * exit command
 */
public class ExitCommand extends Command {
    private final CollectionManager collectionManager;
    public ExitCommand(CollectionManager collectionManager) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.collectionManager = collectionManager;
    }

    /**
     * sets execute flag to false
     * @param argument must be empty
     * @throws InvalidCommandArguments if argument isn't empty
     */
    public void execute(String argument) throws InvalidCommandArguments, IOException {
        if (!argument.isEmpty()) {
            throw new InvalidCommandArguments();
        }
        collectionManager.save();
        Sender.send(new ServerResponse(ExecuteCode.EXIT));
    }
}
