package com.bhma.server.util;

import com.bhma.server.commands.AverageOfHealthCommand;
import com.bhma.server.commands.ClearCommand;
import com.bhma.server.commands.Command;
import com.bhma.server.commands.CountByChapterCommand;
import com.bhma.server.commands.ExecuteScriptCommand;
import com.bhma.server.commands.ExitCommand;
import com.bhma.server.commands.HelpCommand;
import com.bhma.server.commands.InfoCommand;
import com.bhma.server.commands.InsertCommand;
import com.bhma.server.commands.RemoveAnyByWeaponTypeCommand;
import com.bhma.server.commands.RemoveGreaterKeyCommand;
import com.bhma.server.commands.RemoveKeyCommand;
import com.bhma.server.commands.RemoveLowerKeyCommand;
import com.bhma.server.commands.ReplaceIfLowerCommand;
import com.bhma.server.commands.ShowCommand;
import com.bhma.server.commands.UpdateCommand;

import java.util.ArrayList;

/**
 * stores and generates an array list with all currently available commands by CollectionManager, SpaceMarineFiller, InputManager
 * and OutputManager values
 */
public class CommandManager {
    private final ArrayList<Command> commands = new ArrayList<>();

    public CommandManager(CollectionManager collectionManager) {
        commands.add(new AverageOfHealthCommand(collectionManager));
        commands.add(new ClearCommand(collectionManager));
        commands.add(new CountByChapterCommand(collectionManager));
        commands.add(new ExitCommand(collectionManager));
        commands.add(new InfoCommand(collectionManager));
        commands.add(new InsertCommand(collectionManager));
        commands.add(new RemoveAnyByWeaponTypeCommand(collectionManager));
        commands.add(new RemoveGreaterKeyCommand(collectionManager));
        commands.add(new RemoveKeyCommand(collectionManager));
        commands.add(new RemoveLowerKeyCommand(collectionManager));
        commands.add(new ReplaceIfLowerCommand(collectionManager));
        commands.add(new ShowCommand(collectionManager));
        commands.add(new UpdateCommand(collectionManager));
        commands.add(new ExecuteScriptCommand());
        commands.add(new HelpCommand(commands));
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }
}
