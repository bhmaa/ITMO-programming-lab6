package com.bhma.server.commands;

import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;
import java.io.IOException;

/**
 * clear command
 */
public class ClearCommand extends Command {
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * removes all elements from collection
     * @param argument must be empty
     * @throws InvalidCommandArguments if argument isn't empty
     */
    public ServerResponse execute(String argument, Object object) throws InvalidCommandArguments, IOException {
        if (!argument.isEmpty() || object != null) {
            throw new InvalidCommandArguments();
        }
        collectionManager.clear();
        return new ServerResponse(ExecuteCode.SUCCESS);
    }
}
