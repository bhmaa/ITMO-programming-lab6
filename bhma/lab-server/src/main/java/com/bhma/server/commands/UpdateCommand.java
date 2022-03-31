package com.bhma.server.commands;

import com.bhma.common.data.SpaceMarine;
import com.bhma.common.exceptions.IllegalKeyException;
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
import java.nio.channels.DatagramChannel;

/**
 * update command
 */
public class UpdateCommand extends Command {
    private final CollectionManager collectionManager;
    private final DatagramChannel channel;

    public UpdateCommand(CollectionManager collectionManager, DatagramChannel channel) {
        super("update", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
        this.channel = channel;
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
    public void execute(String argument) throws ScriptException, NumberFormatException,
            InvalidCommandArguments, IllegalKeyException, InvalidInputException, IOException, ClassNotFoundException {
        if (argument.isEmpty()) {
            throw new InvalidCommandArguments();
        }
        Long id = Long.valueOf(argument);
        if (!collectionManager.containsId(id)) {
            throw new IllegalKeyException("There's no value with that id.");
        }
        Sender.send(channel, new ServerRequest("server requests space marine value...", CommandRequirement.SPACE_MARINE));
        SpaceMarine spaceMarine = (SpaceMarine) Sender.receiveObject(channel);
        collectionManager.updateID(id, spaceMarine);
        Sender.send(channel, new ServerResponse(ExecuteCode.SUCCESS));
    }
}
