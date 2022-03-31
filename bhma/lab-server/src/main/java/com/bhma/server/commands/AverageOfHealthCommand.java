package com.bhma.server.commands;

import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;
import com.bhma.server.util.Sender;

import java.io.IOException;
import java.nio.channels.DatagramChannel;

/**
 * average_of_health command.
 */
public class AverageOfHealthCommand extends Command {
    private final CollectionManager collectionManager;
    private final DatagramChannel channel;

    public AverageOfHealthCommand(CollectionManager collectionManager, DatagramChannel channel) {
        super("average_of_health", "вывести среднее значение поля health для всех элементов коллекции");
        this.collectionManager = collectionManager;
        this.channel = channel;
    }

    /**
     * print the average value of the health field in collection.
     *
     * @param argument must be empty to execute
     * @throws InvalidCommandArguments if argument isn't empty
     */
    public void execute(String argument) throws InvalidCommandArguments, IOException {
        if (!argument.isEmpty()) {
            throw new InvalidCommandArguments();
        }
        Sender.send(channel, new ServerResponse(String.valueOf(collectionManager.averageOfHealth()), ExecuteCode.VALUE));
    }
}
