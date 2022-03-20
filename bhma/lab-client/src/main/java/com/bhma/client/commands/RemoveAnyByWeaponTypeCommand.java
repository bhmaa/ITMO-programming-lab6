package com.bhma.client.commands;

import com.bhma.client.exceptions.InvalidInputException;
import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.exceptions.ScriptException;
import com.bhma.client.utility.CollectionManager;
import com.bhma.client.utility.SpaceMarineFiller;

/**
 * remove_any_by_weapon_type command
 */
public class RemoveAnyByWeaponTypeCommand extends Command {
    private final CollectionManager collectionManager;
    private final SpaceMarineFiller spaceMarineFiller;

    public RemoveAnyByWeaponTypeCommand(CollectionManager collectionManager, SpaceMarineFiller spaceMarineFiller) {
        super("remove_any_by_weapon_type", "удалить из коллекции один элемент, значение поля weaponType которого эквивалентно заданному");
        this.collectionManager = collectionManager;
        this.spaceMarineFiller = spaceMarineFiller;
    }

    /**
     * removes one element from the collection which weapon type is equal to the entered one
     * @param argument must be empty
     * @throws NoSuchCommandException if argument isn't empty
     * @throws ScriptException if string entered in a file wasn't one of the values from enum WeaponType
     */
    public void execute(String argument) throws NoSuchCommandException, ScriptException, InvalidInputException {
        if (!argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        collectionManager.removeAnyByWeaponType(spaceMarineFiller.fillWeaponType());
    }
}
