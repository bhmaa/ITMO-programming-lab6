package com.bhma.client.commands;

import com.bhma.client.data.SpaceMarine;
import com.bhma.client.exceptions.IllegalKeyException;
import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.exceptions.ScriptException;
import com.bhma.client.utility.CollectionManager;
import com.bhma.client.utility.SpaceMarineFiller;

/**
 * replace_if_lowe command
 */
public class ReplaceIfLoweCommand extends Command {
    private final CollectionManager collectionManager;
    private final SpaceMarineFiller spaceMarineFiller;

    public ReplaceIfLoweCommand(CollectionManager collectionManager, SpaceMarineFiller spaceMarineFiller) {
        super("replace_if_lowe", "заменить значение по ключу, если новое значение меньше старого");
        this.collectionManager = collectionManager;
        this.spaceMarineFiller = spaceMarineFiller;
    }

    /**
     * update value by key if it's greater than entered one
     * @param argument must be a number
     * @throws NoSuchCommandException if argument is empty
     * @throws ScriptException if entered in script element didn't meet the requirements
     * @throws NumberFormatException if argument isn't a number
     * @throws IllegalKeyException if there's no element with entered key in collection
     */
    public void execute(String argument) throws NoSuchCommandException, ScriptException, NumberFormatException, IllegalKeyException {
        if (argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        if (!collectionManager.containsKey(Long.valueOf(argument))) {
            throw new IllegalKeyException("There's no element with that key");
        }
        SpaceMarine newSpaceMarine = spaceMarineFiller.fillSpaceMarine();
        SpaceMarine oldSpaceMarine = collectionManager.getByKey(Long.valueOf(argument));
        if (oldSpaceMarine.compareTo(newSpaceMarine) < 0) {
            collectionManager.addToCollection(Long.valueOf(argument), newSpaceMarine);
        }
    }
}
