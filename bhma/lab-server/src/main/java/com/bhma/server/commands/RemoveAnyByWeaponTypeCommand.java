package com.bhma.server.commands;

import com.bhma.common.data.Weapon;
import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.exceptions.ScriptException;
import com.bhma.common.util.CommandRequirement;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerRequest;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;
import com.bhma.server.util.Sender;

import java.io.IOException;

/**
 * remove_any_by_weapon_type command
 */
public class RemoveAnyByWeaponTypeCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveAnyByWeaponTypeCommand(CollectionManager collectionManager) {
        super("remove_any_by_weapon_type", "удалить из коллекции один элемент, значение поля weaponType которого эквивалентно заданному");
        this.collectionManager = collectionManager;
    }

    /**
     * removes one element from the collection which weapon type is equal to the entered one
     * @param argument must be empty
     * @throws InvalidCommandArguments if argument isn't empty
     * @throws ScriptException if string entered in a file wasn't one of the values from enum WeaponType
     */
    public void execute(String argument) throws InvalidCommandArguments, ScriptException, InvalidInputException,
            IOException, ClassNotFoundException {
        if (!argument.isEmpty()) {
            throw new InvalidCommandArguments();
        }
        Sender.send(new ServerRequest("server requests weapon value...", CommandRequirement.WEAPON));
        Weapon weapon = (Weapon) Sender.receiveObject();
        collectionManager.removeAnyByWeaponType(weapon);
        Sender.send(new ServerResponse(ExecuteCode.SUCCESS));
    }
}
