package com.bhma.client.commands;

import com.bhma.client.data.Weapon;
import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.utility.CollectionManager;

import java.util.Locale;

public class RemoveAnyByWeaponTypeCommand extends Command {
    private CollectionManager collectionManager;

    public RemoveAnyByWeaponTypeCommand(CollectionManager collectionManager) {
        super("remove_any_by_weapon_type", "удалить из коллекции один элемент, значение поля weaponType которого эквивалентно заданному");
        this.collectionManager = collectionManager;
    }

    public void execute(String argument) throws NoSuchCommandException, IllegalArgumentException {
        if (argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        collectionManager.removeAnyByWeaponType(Weapon.valueOf(argument.toUpperCase(Locale.ROOT)));
    }
}
