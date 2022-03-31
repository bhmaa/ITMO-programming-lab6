package com.bhma.server.commands;

import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;
import com.bhma.server.util.Sender;

import java.io.IOException;
import java.nio.channels.DatagramChannel;

/**
 * clear command
 */
public class ClearCommand extends Command {
    private final CollectionManager collectionManager;
    private final DatagramChannel channel;

    public ClearCommand(CollectionManager collectionManager, DatagramChannel channel) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
        this.channel = channel;
    }

    /**
     * removes all elements from collection
     * @param argument must be empty
     * @throws InvalidCommandArguments if argument isn't empty
     */
    public void execute(String argument) throws InvalidCommandArguments, IOException {
        if (!argument.isEmpty()) {
            throw new InvalidCommandArguments();
        }
        collectionManager.clear();
        Sender.send(channel, new ServerResponse(ExecuteCode.SUCCESS));
    }
}
