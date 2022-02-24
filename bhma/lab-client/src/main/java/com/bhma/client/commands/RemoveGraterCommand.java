package com.bhma.client.commands;

import com.bhma.client.data.SpaceMarine;
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

    public void execute(String argument) throws NoSuchCommandException, ScriptException {
        if (!argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        SpaceMarine newSpaceMarine = spaceMarineFiller.fillSpaceMarine();
        collectionManager.removeGreater(newSpaceMarine);
    }
}
