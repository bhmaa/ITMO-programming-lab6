package com.bhma.server.commands;

import com.bhma.common.data.SpaceMarine;
import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.exceptions.ScriptException;
import com.bhma.common.util.CommandRequirement;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerRequest;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;
import com.bhma.server.util.Sender;

import java.io.IOException;

public class RemoveGreaterKeyCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveGreaterKeyCommand(CollectionManager collectionManager) {
        super("remove_greater_key", "удалить из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
    }

    /**
     * removes all elements that greater than entered one
     * @param argument must be empty
     * @throws InvalidCommandArguments if argument isn't empty
     * @throws ScriptException if entered in script element didn't meet the requirements
     */
    public void execute(String argument) throws InvalidCommandArguments,
            ScriptException, InvalidInputException, IOException, ClassNotFoundException {
        if (!argument.isEmpty()) {
            throw new InvalidCommandArguments();
        }
        Sender.send(new ServerRequest("server requests space marine value...", CommandRequirement.SPACE_MARINE));
        SpaceMarine spaceMarine = (SpaceMarine) Sender.receiveObject();
        collectionManager.removeGreater(spaceMarine);
        Sender.send(new ServerResponse(ExecuteCode.SUCCESS));
    }
}
