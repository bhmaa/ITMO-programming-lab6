package com.bhma.server.commands;

import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.util.CommandRequirement;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;
import java.io.IOException;

/**
 * average_of_health command.
 */
public class AverageOfHealthCommand extends Command {
    private final CollectionManager collectionManager;

    public AverageOfHealthCommand(CollectionManager collectionManager) {
        super("average_of_health", "вывести среднее значение поля health для всех элементов коллекции",
                CommandRequirement.NONE);
        this.collectionManager = collectionManager;
    }

    /**
     * print the average value of the health field in collection.
     *
     * @param argument must be empty to execute
     * @throws InvalidCommandArguments if argument isn't empty
     */
    public ServerResponse execute(String argument, Object object) throws InvalidCommandArguments, IOException {
        if (!argument.isEmpty() || object != null) {
            throw new InvalidCommandArguments();
        }
        return new ServerResponse(String.valueOf(collectionManager.averageOfHealth()), ExecuteCode.VALUE);
    }
}
