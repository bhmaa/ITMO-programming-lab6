package com.bhma.client.commands;

import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.utility.CollectionManager;

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
     * @throws NoSuchCommandException if argument isn't empty
     */
    public void execute(String argument) throws NoSuchCommandException {
        if (!argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        collectionManager.clear();
    }
}
