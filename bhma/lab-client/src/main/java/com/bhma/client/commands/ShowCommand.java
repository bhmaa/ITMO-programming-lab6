package com.bhma.client.commands;

import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.utility.CollectionManager;
import com.bhma.client.utility.OutputManager;

/**
 * show command
 */
public class ShowCommand extends Command {
    private final CollectionManager collectionManager;
    private final OutputManager outputManager;

    public ShowCommand(CollectionManager collectionManager, OutputManager outputManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    /**
     * print all elements of collection in a string representation
     * @param argument must be empty
     * @throws NoSuchCommandException if argument isn't empty
     */
    public void execute(String argument) throws NoSuchCommandException {
        if (!argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        outputManager.printlnImportantMessage(collectionManager.toString());
    }
}
