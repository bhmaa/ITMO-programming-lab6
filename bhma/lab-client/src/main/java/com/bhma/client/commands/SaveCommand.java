package com.bhma.client.commands;

import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.utility.CollectionManager;

/**
 * save command
 */
public class SaveCommand extends Command {
    private final CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
    }

    /**
     * saves collection to a file
     * @param argument must be empty
     * @throws NoSuchCommandException if argument isn't empty
     */
    public void execute(String argument) throws NoSuchCommandException {
        if (!argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        collectionManager.save();
    }
}
