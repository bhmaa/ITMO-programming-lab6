package com.bhma.server.commands;

import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;
import com.bhma.server.util.Sender;

import java.io.IOException;
import java.nio.channels.DatagramChannel;

/**
 * exit command
 */
public class ExitCommand extends Command {
    private final CollectionManager collectionManager;
    private final DatagramChannel channel;

    public ExitCommand(CollectionManager collectionManager, DatagramChannel channel) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.collectionManager = collectionManager;
        this.channel = channel;
    }

    /**
     * sets execute flag to false
     * @param argument must be empty
     * @throws InvalidCommandArguments if argument isn't empty
     */
    public void execute(String argument) throws InvalidCommandArguments, IOException {
        if (!argument.isEmpty()) {
            throw new InvalidCommandArguments();
        }
        collectionManager.save(channel);
        Sender.send(channel, new ServerResponse(ExecuteCode.EXIT));
    }
}
