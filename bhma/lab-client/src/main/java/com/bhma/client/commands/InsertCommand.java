package com.bhma.client.commands;


import com.bhma.client.exceptions.IllegalValueException;
import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.exceptions.ScriptException;
import com.bhma.client.utility.CollectionManager;
import com.bhma.client.utility.SpaceMarineFiller;

public class InsertCommand extends Command {
    private final CollectionManager collectionManager;
    private final SpaceMarineFiller spaceMarineFiller;

    public InsertCommand(CollectionManager collectionManager, SpaceMarineFiller spaceMarineFiller) {
        super("insert", "добавить новый элемент с заданным ключом");
        this.collectionManager = collectionManager;
        this.spaceMarineFiller = spaceMarineFiller;
    }

    public void execute(String argument) throws ScriptException, NoSuchCommandException, NumberFormatException, IllegalValueException {
        if (argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        if (collectionManager.getCollection().containsKey(Long.valueOf(argument))) {
            throw new IllegalValueException("Element with this ID is already exist");
        }
        collectionManager.addToCollection(Long.valueOf(argument), spaceMarineFiller.fillSpaceMarine());
    }
}
