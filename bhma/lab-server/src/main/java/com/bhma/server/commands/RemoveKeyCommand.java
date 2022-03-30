package com.bhma.server.commands;

import com.bhma.common.exceptions.IllegalKeyException;
import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;
import com.bhma.server.util.Sender;

import java.io.IOException;

/**
 * remove_key command
 */
public class RemoveKeyCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveKeyCommand(CollectionManager collectionManager) {
        super("remove_key", "удалить элемент из коллекции по его ключу");
        this.collectionManager = collectionManager;
    }

    /**
     * removes element from collection by key
     * @param argument must be a number
     * @throws InvalidCommandArguments if argument is empty
     * @throws NumberFormatException if argument is not a number
     * @throws IllegalKeyException if there's no element with entered key
     */
    public void execute(String argument) throws InvalidCommandArguments,
            NumberFormatException, IllegalKeyException, IOException {
        if (argument.isEmpty()) {
            throw new InvalidCommandArguments();
        }
        if (!collectionManager.containsKey(Long.valueOf(argument))) {
            throw new IllegalKeyException("There's no value with that key.");
        }
        collectionManager.remove(Long.valueOf(argument));
        Sender.send(new ServerResponse(ExecuteCode.SUCCESS));
    }
}
