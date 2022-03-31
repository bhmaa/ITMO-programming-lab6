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
 * insert command
 */
public class InsertCommand extends Command {
    private final CollectionManager collectionManager;
    private final DatagramChannel channel;

    public InsertCommand(CollectionManager collectionManager, DatagramChannel channel) {
        super("insert", "добавить новый элемент с заданным ключом");
        this.collectionManager = collectionManager;
        this.channel = channel;
    }

    /**
     * add to collection element with entered key
     * @param argument must be a number (long)
     * @throws InvalidCommandArguments if argument is empty
     * @throws NumberFormatException if argument is not a number
     * @throws IllegalKeyException if there is an element with equal key in collection
     * @throws ScriptException if entered in script element didn't meet the requirements
     */
    public void execute(String argument) throws ScriptException, InvalidCommandArguments, NumberFormatException,
            IllegalKeyException, InvalidInputException, IOException, ClassNotFoundException {
        if (argument.isEmpty()) {
            throw new InvalidCommandArguments();
        }
        if (collectionManager.getCollection().containsKey(Long.valueOf(argument))) {
            throw new IllegalKeyException("Element with this ID is already exist");
        }
        Sender.send(channel, new ServerRequest("server requests space marine value...", CommandRequirement.SPACE_MARINE));
        SpaceMarine spaceMarine = (SpaceMarine) Sender.receiveObject(channel);
        collectionManager.addToCollection(Long.valueOf(argument), spaceMarine);
        Sender.send(channel, new ServerResponse(ExecuteCode.SUCCESS));
    }
}
