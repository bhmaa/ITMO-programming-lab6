package com.bhma.server.commands;

import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;
import com.bhma.server.util.Sender;

import java.io.IOException;
import java.nio.channels.DatagramChannel;

/**
 * remove_lower_key command
 */
public class RemoveLowerKeyCommand extends Command {
    private final CollectionManager collectionManager;
    private final DatagramChannel channel;

    public RemoveLowerKeyCommand(CollectionManager collectionManager, DatagramChannel channel) {
        super("remove_lower_key", "удалить из коллекции все элементы, ключ которых меньше, чем заданный");
        this.collectionManager = collectionManager;
        this.channel = channel;
    }

    /**
     * removes all elements whose key is lower than entered one
     * @param argument must be a number
     * @throws InvalidCommandArguments if argument is empty
     * @throws NumberFormatException if argument isn't a number
     */
    public void execute(String argument) throws InvalidCommandArguments, NumberFormatException, IOException {
        if (argument.isEmpty()) {
            throw new InvalidCommandArguments();
        }
        collectionManager.removeLowerKey(Long.valueOf(argument));
        Sender.send(channel, new ServerResponse(ExecuteCode.SUCCESS));
    }
}
