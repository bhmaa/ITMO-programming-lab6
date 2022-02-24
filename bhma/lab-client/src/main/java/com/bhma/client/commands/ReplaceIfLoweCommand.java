package com.bhma.client.commands;

import com.bhma.client.data.SpaceMarine;
import com.bhma.client.exceptions.IllegalValueException;
import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.exceptions.ScriptException;
import com.bhma.client.utility.CollectionManager;
import com.bhma.client.utility.SpaceMarineFiller;

public class ReplaceIfLoweCommand extends Command {
    private final CollectionManager collectionManager;
    private final SpaceMarineFiller spaceMarineFiller;

    public ReplaceIfLoweCommand(CollectionManager collectionManager, SpaceMarineFiller spaceMarineFiller) {
        super("replace_if_lowe", "заменить значение по ключу, если новое значение меньше старого");
        this.collectionManager = collectionManager;
        this.spaceMarineFiller = spaceMarineFiller;
    }

    public void execute(String argument) throws NoSuchCommandException, ScriptException, NumberFormatException, IllegalValueException {
        if (argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        if (!collectionManager.containsKey(Long.valueOf(argument))) {
            throw new IllegalValueException("There's no element with that key");
        }
        SpaceMarine newSpaceMarine = spaceMarineFiller.fillSpaceMarine();
        SpaceMarine oldSpaceMarine = collectionManager.getByKey(Long.valueOf(argument));
        if (oldSpaceMarine.compare(newSpaceMarine) < 0) {
            collectionManager.addToCollection(Long.valueOf(argument), newSpaceMarine);
        }
    }
}
