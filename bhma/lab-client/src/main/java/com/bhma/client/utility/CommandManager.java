package com.bhma.client.utility;

import com.bhma.client.commands.*;

import java.util.ArrayList;

/**
 * stores and generates an array list with all currently available commands by CollectionManager, SpaceMarineFiller, InputManager
 * and OutputManager values
 */
public class CommandManager {
    private final ArrayList<Command> commands = new ArrayList<>();

    public CommandManager(CollectionManager collectionManager, SpaceMarineFiller spaceMarineFiller,
                          InputManager inputManager, OutputManager outputManager) {
        commands.add(new AverageOfHealthCommand(collectionManager, outputManager));
        commands.add(new ClearCommand(collectionManager));
        commands.add(new CountByChapterCommand(collectionManager, spaceMarineFiller, outputManager));
        commands.add(new ExitCommand());
        commands.add(new InfoCommand(collectionManager));
        commands.add(new InsertCommand(collectionManager, spaceMarineFiller));
        commands.add(new RemoveAnyByWeaponTypeCommand(collectionManager));
        commands.add(new RemoveGraterCommand(collectionManager, spaceMarineFiller));
        commands.add(new RemoveKeyCommand(collectionManager));
        commands.add(new RemoveLowerKeyCommand(collectionManager));
        commands.add(new ReplaceIfLoweCommand(collectionManager, spaceMarineFiller));
        commands.add(new SaveCommand(collectionManager));
        commands.add(new ShowCommand(collectionManager));
        commands.add(new UpdateCommand(collectionManager, spaceMarineFiller));
        commands.add(new ExecuteScriptCommand(inputManager));
        commands.add(new HelpCommand(commands, outputManager));
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }
}
