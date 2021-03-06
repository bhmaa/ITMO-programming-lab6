package com.bhma.server.commands;

import com.bhma.common.data.Weapon;
import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.util.CommandRequirement;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;
import java.io.IOException;

/**
 * remove_any_by_weapon_type command
 */
public class RemoveAnyByWeaponTypeCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveAnyByWeaponTypeCommand(CollectionManager collectionManager) {
        super("remove_any_by_weapon_type", "удалить из коллекции один элемент, значение поля weaponType"
                + " которого эквивалентно заданному", CommandRequirement.WEAPON);
        this.collectionManager = collectionManager;
    }

    /**
     * removes one element from the collection which weapon type is equal to the entered one
     * @param argument must be empty
     * @throws InvalidCommandArguments if argument isn't empty
     */
    public ServerResponse execute(String argument, Object weapon) throws InvalidCommandArguments, IOException,
            ClassNotFoundException {
        if (!argument.isEmpty() || weapon == null || weapon.getClass() != Weapon.class) {
            throw new InvalidCommandArguments();
        }
        collectionManager.removeAnyByWeaponType((Weapon) weapon);
        return new ServerResponse(ExecuteCode.SUCCESS);
    }
}
