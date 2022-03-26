package com.bhma.client.commands;

import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.utility.CollectionManager;
import com.bhma.client.utility.OutputManager;

/**
 * save command
 */
public class SaveCommand extends Command {
    private final CollectionManager collectionManager;
    private final OutputManager outputManager;

    public SaveCommand(CollectionManager collectionManager, OutputManager outputManager) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
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
        collectionManager.save(outputManager);
    }
}
