package com.bhma.client.commands;

import com.bhma.client.data.SpaceMarine;
import com.bhma.client.exceptions.IllegalKeyException;
import com.bhma.client.exceptions.InvalidInputException;
import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.exceptions.ScriptException;
import com.bhma.client.utility.CollectionManager;
import com.bhma.client.utility.SpaceMarineFiller;

/**
 * update command
 */
public class UpdateCommand extends Command {
    private final CollectionManager collectionManager;
    private final SpaceMarineFiller spaceMarineFiller;

    public UpdateCommand(CollectionManager collectionManager, SpaceMarineFiller spaceMarineFiller) {
        super("update", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
        this.spaceMarineFiller = spaceMarineFiller;
    }

    /**
     * updates element of collection whose id equal entered one
     * @param argument must be a number
     * @throws ScriptException if entered in script element didn't meet the requirements
     * @throws NumberFormatException if argument is not a number
     * @throws NoSuchCommandException if argument is empty
     * @throws IllegalKeyException if there's no element with entered id
     */
    public void execute(String argument) throws ScriptException, NumberFormatException, NoSuchCommandException, IllegalKeyException, InvalidInputException {
        if (argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        Long id = Long.valueOf(argument);
        if (!collectionManager.containsId(id)) {
            throw new IllegalKeyException("There's no value with that id.");
        } else {
            SpaceMarine instance = collectionManager.getById(id);
            instance.setName(spaceMarineFiller.fillName());
            instance.setCoordinates(spaceMarineFiller.fillCoordinates());
            instance.setHealth(spaceMarineFiller.fillHealth());
            instance.setCategory(spaceMarineFiller.fillCategory());
            instance.setWeaponType(spaceMarineFiller.fillWeaponType());
            instance.setMeleeWeapon(spaceMarineFiller.fillMeleeWeapon());
            instance.setChapter(spaceMarineFiller.fillChapter());
        }
    }
}
