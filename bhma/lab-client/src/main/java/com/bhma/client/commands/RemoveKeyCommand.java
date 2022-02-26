package com.bhma.client.commands;

import com.bhma.client.exceptions.IllegalKeyException;
import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.utility.CollectionManager;

/**
 * remove_key command
 */
public class RemoveKeyCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveKeyCommand(CollectionManager collectionManager) {
        super("remove_key", "удалить элемент из коллекции по его ключу");
        this.collectionManager = collectionManager;
    }

    /**
     * removes element from collection by key
     * @param argument must be a number
     * @throws NoSuchCommandException if argument is empty
     * @throws NumberFormatException if argument is not a number
     * @throws IllegalKeyException if there's no element with entered key
     */
    public void execute(String argument) throws NoSuchCommandException, NumberFormatException, IllegalKeyException {
        if (argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        if (!collectionManager.containsKey(Long.valueOf(argument))) {
            throw new IllegalKeyException("There's no value with that key.");
        }
        collectionManager.remove(Long.valueOf(argument));
    }
}
