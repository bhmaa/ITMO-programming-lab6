package com.bhma.client.commands;

import com.bhma.client.data.SpaceMarine;
import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.exceptions.ScriptException;
import com.bhma.client.utility.CollectionManager;
import com.bhma.client.utility.SpaceMarineFiller;

public class UpdateCommand extends Command {
    private final CollectionManager collectionManager;
    private final SpaceMarineFiller spaceMarineFiller;

    public UpdateCommand(CollectionManager collectionManager, SpaceMarineFiller spaceMarineFiller) {
        super("update", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
        this.spaceMarineFiller = spaceMarineFiller;
    }

    public void execute(String argument) throws ScriptException, NumberFormatException, NoSuchCommandException {
        if (argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        Long id = Long.valueOf(argument);
        if (!collectionManager.containsId(id)) {
            System.out.println("There's no value with that id.");
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
