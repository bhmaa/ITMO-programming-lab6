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
 * update command
 */
public class UpdateCommand extends Command {
    private final CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update", "обновить значение элемента коллекции, id которого равен заданному",
                CommandRequirement.SPACE_MARINE, "server requests space marine value...");
        this.collectionManager = collectionManager;
    }

    /**
     * updates element of collection whose id equal entered one
     *
     * @param argument must be a number
     * @throws ScriptException         if entered in script element didn't meet the requirements
     * @throws NumberFormatException   if argument is not a number
     * @throws InvalidCommandArguments if argument is empty
     * @throws IllegalKeyException     if there's no element with entered id
     */
    public ServerResponse execute(String argument, Object spaceMarine) throws ScriptException, NumberFormatException,
            InvalidCommandArguments, IllegalKeyException, InvalidInputException, IOException, ClassNotFoundException {
        if (argument.isEmpty() || spaceMarine == null) {
            throw new InvalidCommandArguments();
        }
        Long id = Long.valueOf(argument);
        if (!collectionManager.containsId(id)) {
            throw new IllegalKeyException("There's no value with that id.");
        }
        collectionManager.updateID(id, (SpaceMarine) spaceMarine);
        return new ServerResponse(ExecuteCode.SUCCESS);
    }
}
