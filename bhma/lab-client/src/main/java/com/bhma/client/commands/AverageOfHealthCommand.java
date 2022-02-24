package com.bhma.client.commands;

import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.utility.CollectionManager;
import com.bhma.client.utility.OutputManager;

/**
 * average_of_health command.
 */
public class AverageOfHealthCommand extends Command {
    private final CollectionManager collectionManager;
    private final OutputManager outputManager;

    public AverageOfHealthCommand(CollectionManager collectionManager, OutputManager outputManager) {
        super("average_of_health", "вывести среднее значение поля health для всех элементов коллекции");
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    /**
     * print the average value of the health field in collection.
     *
     * @param argument must be empty to execute
     * @throws NoSuchCommandException if argument isn't empty
     */
    public void execute(String argument) throws NoSuchCommandException {
        if (!argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        outputManager.println(String.valueOf(collectionManager.averageOfHealth()));
    }
}
