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

import java.nio.channels.DatagramChannel;
import java.util.ArrayList;

/**
 * stores and generates an array list with all currently available commands by CollectionManager, SpaceMarineFiller, InputManager
 * and OutputManager values
 */
public class CommandManager {
    private final ArrayList<Command> commands = new ArrayList<>();

    public CommandManager(CollectionManager collectionManager, DatagramChannel channel) {
        commands.add(new AverageOfHealthCommand(collectionManager, channel));
        commands.add(new ClearCommand(collectionManager, channel));
        commands.add(new CountByChapterCommand(collectionManager, channel));
        commands.add(new ExitCommand(collectionManager, channel));
        commands.add(new InfoCommand(collectionManager, channel));
        commands.add(new InsertCommand(collectionManager, channel));
        commands.add(new RemoveAnyByWeaponTypeCommand(collectionManager, channel));
        commands.add(new RemoveGreaterKeyCommand(collectionManager, channel));
        commands.add(new RemoveKeyCommand(collectionManager, channel));
        commands.add(new RemoveLowerKeyCommand(collectionManager, channel));
        commands.add(new ReplaceIfLowerCommand(collectionManager, channel));
        commands.add(new ShowCommand(collectionManager, channel));
        commands.add(new UpdateCommand(collectionManager, channel));
        commands.add(new ExecuteScriptCommand(channel));
        commands.add(new HelpCommand(commands, channel));
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }
}
