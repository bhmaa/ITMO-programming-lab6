package com.bhma.server.commands;

import com.bhma.common.data.SpaceMarine;
import com.bhma.common.exceptions.IllegalKeyException;
import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.exceptions.ScriptException;
import com.bhma.common.util.CommandRequirement;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;
import java.io.IOException;

/**
 * replace_if_lowe command
 */
public class ReplaceIfLowerCommand extends Command {
    private final CollectionManager collectionManager;

    public ReplaceIfLowerCommand(CollectionManager collectionManager) {
        super("replace_if_lower", "заменить значение по ключу, если новое значение меньше старого",
                CommandRequirement.SPACE_MARINE, "server requests space marine value...");
        this.collectionManager = collectionManager;
    }

    /**
     * update value by key if it's greater than entered one
     * @param argument must be a number
     * @throws InvalidCommandArguments if argument is empty
     * @throws ScriptException if entered in script element didn't meet the requirements
     * @throws NumberFormatException if argument isn't a number
     * @throws IllegalKeyException if there's no element with entered key in collection
     */
    public ServerResponse execute(String argument, Object spaceMarine) throws InvalidCommandArguments, ScriptException,
            NumberFormatException, IllegalKeyException, InvalidInputException, IOException, ClassNotFoundException {
        if (argument.isEmpty() || spaceMarine == null) {
            throw new InvalidCommandArguments();
        }
        if (!collectionManager.containsKey(Long.valueOf(argument))) {
            throw new IllegalKeyException("There's no element with that key");
        }
        SpaceMarine oldSpaceMarine = collectionManager.getByKey(Long.valueOf(argument));
        if (oldSpaceMarine.compareTo((SpaceMarine) spaceMarine) < 0) {
            collectionManager.addToCollection(Long.valueOf(argument), (SpaceMarine) spaceMarine);
        }
        return new ServerResponse(ExecuteCode.SUCCESS);
    }
}
