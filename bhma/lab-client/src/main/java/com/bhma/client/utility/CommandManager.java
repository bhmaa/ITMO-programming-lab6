package com.bhma.client.utility;

import com.bhma.client.commands.AverageOfHealthCommand;
import com.bhma.client.commands.ClearCommand;
import com.bhma.client.commands.Command;
import com.bhma.client.commands.CountByChapterCommand;
import com.bhma.client.commands.ExecuteScriptCommand;
import com.bhma.client.commands.ExitCommand;
import com.bhma.client.commands.HelpCommand;
import com.bhma.client.commands.InfoCommand;
import com.bhma.client.commands.InsertCommand;
import com.bhma.client.commands.RemoveAnyByWeaponTypeCommand;
import com.bhma.client.commands.RemoveGraterCommand;
import com.bhma.client.commands.RemoveKeyCommand;
import com.bhma.client.commands.RemoveLowerKeyCommand;
import com.bhma.client.commands.ReplaceIfLoweCommand;
import com.bhma.client.commands.SaveCommand;
import com.bhma.client.commands.ShowCommand;
import com.bhma.client.commands.UpdateCommand;

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
        commands.add(new InfoCommand(collectionManager, outputManager));
        commands.add(new InsertCommand(collectionManager, spaceMarineFiller));
        commands.add(new RemoveAnyByWeaponTypeCommand(collectionManager, spaceMarineFiller));
        commands.add(new RemoveGraterCommand(collectionManager, spaceMarineFiller));
        commands.add(new RemoveKeyCommand(collectionManager));
        commands.add(new RemoveLowerKeyCommand(collectionManager));
        commands.add(new ReplaceIfLoweCommand(collectionManager, spaceMarineFiller));
        commands.add(new SaveCommand(collectionManager, outputManager));
        commands.add(new ShowCommand(collectionManager, outputManager));
        commands.add(new UpdateCommand(collectionManager, spaceMarineFiller));
        commands.add(new ExecuteScriptCommand(inputManager));
        commands.add(new HelpCommand(commands, outputManager));
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }
}
