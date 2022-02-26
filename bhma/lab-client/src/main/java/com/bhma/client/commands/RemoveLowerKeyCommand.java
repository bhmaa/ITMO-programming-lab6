package com.bhma.client.commands;

import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.utility.CollectionManager;

/**
 * remove_lower_key command
 */
public class RemoveLowerKeyCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveLowerKeyCommand(CollectionManager collectionManager) {
        super("remove_lower_key", "удалить из коллекции все элементы, ключ которых меньше, чем заданный");
        this.collectionManager = collectionManager;
    }

    /**
     * removes all elements whose key is lower than entered one
     * @param argument must be a number
     * @throws NoSuchCommandException if argument is empty
     * @throws NumberFormatException if argument isn't a number
     */
    public void execute(String argument) throws NoSuchCommandException, NumberFormatException {
        if (argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        collectionManager.removeLowerKey(Long.valueOf(argument));
    }
}
