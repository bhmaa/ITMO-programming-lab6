package com.bhma.server.commands;

import com.bhma.common.data.SpaceMarine;
import com.bhma.common.exceptions.IllegalKeyException;
import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.exceptions.ScriptException;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;
import java.io.IOException;

/**
 * insert command
 */
public class InsertCommand extends Command {
    private final CollectionManager collectionManager;

    public InsertCommand(CollectionManager collectionManager) {
        super("insert", "добавить новый элемент с заданным ключом"
        );
        this.collectionManager = collectionManager;
    }

    /**
     * add to collection element with entered key
     * @param argument must be a number (long)
     * @throws InvalidCommandArguments if argument is empty
     * @throws NumberFormatException if argument is not a number
     * @throws IllegalKeyException if there is an element with equal key in collection
     * @throws ScriptException if entered in script element didn't meet the requirements
     */
    public ServerResponse execute(String argument, Object spaceMarine) throws ScriptException, InvalidCommandArguments,
            NumberFormatException, IllegalKeyException, InvalidInputException, IOException, ClassNotFoundException {
        if (argument.isEmpty() || spaceMarine == null || spaceMarine.getClass() != SpaceMarine.class) {
            throw new InvalidCommandArguments();
        }
        if (collectionManager.getCollection().containsKey(Long.valueOf(argument))) {
            throw new IllegalKeyException("Element with this ID is already exist");
        }
        collectionManager.addToCollection(Long.valueOf(argument), (SpaceMarine) spaceMarine);
        return new ServerResponse(ExecuteCode.SUCCESS);
    }
}
