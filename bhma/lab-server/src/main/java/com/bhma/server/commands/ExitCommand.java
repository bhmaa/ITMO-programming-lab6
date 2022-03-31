package com.bhma.server.commands;

import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.util.CommandRequirement;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * exit command
 */
public class ExitCommand extends Command {
    private final CollectionManager collectionManager;

    public ExitCommand(CollectionManager collectionManager) {
        super("exit", "завершить программу (без сохранения в файл)", CommandRequirement.NONE);
        this.collectionManager = collectionManager;
    }

    /**
     * sets execute flag to false
     * @param argument must be empty
     * @throws InvalidCommandArguments if argument isn't empty
     */
    public ServerResponse execute(String argument) throws InvalidCommandArguments, IOException, JAXBException {
        if (!argument.isEmpty()) {
            throw new InvalidCommandArguments();
        }
        collectionManager.save();
        return new ServerResponse(ExecuteCode.EXIT);
    }
}
