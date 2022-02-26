package com.bhma.client.commands;

import com.bhma.client.exceptions.IllegalValueException;
import com.bhma.client.exceptions.IllegalKeyException;
import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.utility.CollectionManager;

public class RemoveKeyCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveKeyCommand(CollectionManager collectionManager) {
        super("remove_key", "удалить элемент из коллекции по его ключу");
        this.collectionManager = collectionManager;
    }

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
