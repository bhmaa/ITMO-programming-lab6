package com.bhma.client.commands;

import com.bhma.client.exceptions.IllegalKeyException;
import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.exceptions.ScriptException;
import com.bhma.client.utility.CollectionManager;
import com.bhma.client.utility.SpaceMarineFiller;

/**
 * insert command
 */
public class InsertCommand extends Command {
    private final CollectionManager collectionManager;
    private final SpaceMarineFiller spaceMarineFiller;

    public InsertCommand(CollectionManager collectionManager, SpaceMarineFiller spaceMarineFiller) {
        super("insert", "добавить новый элемент с заданным ключом");
        this.collectionManager = collectionManager;
        this.spaceMarineFiller = spaceMarineFiller;
    }

    /**
     * add to collection element with entered key
     * @param argument must be a number (long)
     * @throws NoSuchCommandException if argument is empty
     * @throws NumberFormatException if argument is not a number
     * @throws IllegalKeyException if there is an element with equal key in collection
     * @throws ScriptException if entered in script element didn't meet the requirements
     */
    public void execute(String argument) throws ScriptException, NoSuchCommandException, NumberFormatException, IllegalKeyException {
        if (argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        if (collectionManager.getCollection().containsKey(Long.valueOf(argument))) {
            throw new IllegalKeyException("Element with this ID is already exist");
        }
        collectionManager.addToCollection(Long.valueOf(argument), spaceMarineFiller.fillSpaceMarine());
    }
}
