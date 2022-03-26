package com.bhma.client.commands;

import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.utility.CollectionManager;
import com.bhma.client.utility.OutputManager;

/**
 * info command
 */
public class InfoCommand extends Command {
    private final CollectionManager collectionManager;
    private final OutputManager outputManager;

    public InfoCommand(CollectionManager collectionManager, OutputManager outputManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    /**
     * print class of collection, it's creation date and size
     * @param argument must be empty
     * @throws NoSuchCommandException if argument isn't empty
     */
    public void execute(String argument) throws NoSuchCommandException {
        if (!argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        outputManager.printlnImportantMessage(collectionManager.collectionInfo());
    }
}
