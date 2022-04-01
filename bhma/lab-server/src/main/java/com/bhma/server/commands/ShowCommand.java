package com.bhma.server.commands;

import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;
import java.io.IOException;

/**
 * show command
 */
public class ShowCommand extends Command {
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении"
        );
        this.collectionManager = collectionManager;
    }

    /**
     * print all elements of collection in a string representation
     *
     * @param argument must be empty
     * @throws InvalidCommandArguments if argument isn't empty
     */
    public ServerResponse execute(String argument, Object object) throws InvalidCommandArguments, IOException {
        if (!argument.isEmpty() || object != null) {
            throw new InvalidCommandArguments();
        }
        return new ServerResponse(collectionManager.toString(), ExecuteCode.VALUE);
    }
}
