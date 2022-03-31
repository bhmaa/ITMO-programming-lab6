package com.bhma.server.commands;

import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;
import com.bhma.server.util.Sender;

import java.io.IOException;
import java.nio.channels.DatagramChannel;

/**
 * show command
 */
public class ShowCommand extends Command {
    private final CollectionManager collectionManager;
    private final DatagramChannel channel;

    public ShowCommand(CollectionManager collectionManager, DatagramChannel channel) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
        this.channel = channel;
    }

    /**
     * print all elements of collection in a string representation
     *
     * @param argument must be empty
     * @throws InvalidCommandArguments if argument isn't empty
     */
    public void execute(String argument) throws InvalidCommandArguments, IOException {
        if (!argument.isEmpty()) {
            throw new InvalidCommandArguments();
        }
        Sender.send(channel, new ServerResponse(collectionManager.toString(), ExecuteCode.VALUE));
    }
}
