package com.bhma.client.commands;

import com.bhma.client.data.SpaceMarine;
import com.bhma.client.exceptions.InvalidInputException;
import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.exceptions.ScriptException;
import com.bhma.client.utility.CollectionManager;
import com.bhma.client.utility.SpaceMarineFiller;

public class RemoveGraterCommand extends Command {
    private final CollectionManager collectionManager;
    private final SpaceMarineFiller spaceMarineFiller;

    public RemoveGraterCommand(CollectionManager collectionManager, SpaceMarineFiller spaceMarineFiller) {
        super("remove_greater_key", "удалить из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
        this.spaceMarineFiller = spaceMarineFiller;
    }

    /**
     * removes all elements that greater than entered one
     * @param argument must be empty
     * @throws NoSuchCommandException if argument isn't empty
     * @throws ScriptException if entered in script element didn't meet the requirements
     */
    public void execute(String argument) throws NoSuchCommandException, ScriptException, InvalidInputException {
        if (!argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        SpaceMarine newSpaceMarine = spaceMarineFiller.fillSpaceMarine();
        collectionManager.removeGreater(newSpaceMarine);
    }
}
