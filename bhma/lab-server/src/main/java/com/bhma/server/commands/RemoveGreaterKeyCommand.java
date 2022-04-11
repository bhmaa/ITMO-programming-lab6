package com.bhma.server.commands;

import com.bhma.common.data.SpaceMarine;
import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;
import java.io.IOException;

public class RemoveGreaterKeyCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveGreaterKeyCommand(CollectionManager collectionManager) {
        super("remove_greater_key", "удалить из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
    }

    /**
     * removes all elements that greater than entered one
     * @param argument must be empty
     * @throws InvalidCommandArguments if argument isn't empty
     */
    public ServerResponse execute(String argument, Object spaceMarine) throws InvalidCommandArguments,
            IOException, ClassNotFoundException {
        if (!argument.isEmpty() || spaceMarine == null || spaceMarine.getClass() != SpaceMarine.class) {
            throw new InvalidCommandArguments();
        }
        collectionManager.removeGreater((SpaceMarine) spaceMarine);
        return new ServerResponse(ExecuteCode.SUCCESS);
    }
}
