package com.bhma.server.util;

import com.bhma.common.util.CommandRequirement;
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
import com.bhma.server.commands.SaveCommand;
import com.bhma.server.commands.ShowCommand;
import com.bhma.server.commands.UpdateCommand;
import java.util.HashMap;

/**
 * stores and generates an array list with all currently available commands by CollectionManager, SpaceMarineFiller, InputManager
 * and OutputManager values
 */
public class CommandManager {
    private final HashMap<String, Command> commands = new HashMap<>();
    private final HashMap<String, CommandRequirement> requirements = new HashMap<>();
    private final SaveCommand saveCommand;

    public CommandManager(CollectionManager collectionManager) {
        AverageOfHealthCommand averageOfHealthCommand = new AverageOfHealthCommand(collectionManager);
        commands.put(averageOfHealthCommand.getName(), averageOfHealthCommand);
        ClearCommand clearCommand = new ClearCommand(collectionManager);
        commands.put(clearCommand.getName(), clearCommand);
        CountByChapterCommand countByChapterCommand = new CountByChapterCommand(collectionManager);
        commands.put(countByChapterCommand.getName(), countByChapterCommand);
        ExitCommand exitCommand = new ExitCommand(collectionManager);
        commands.put(exitCommand.getName(), exitCommand);
        InfoCommand infoCommand = new InfoCommand(collectionManager);
        commands.put(infoCommand.getName(), infoCommand);
        InsertCommand insertCommand = new InsertCommand(collectionManager);
        commands.put(insertCommand.getName(), insertCommand);
        RemoveAnyByWeaponTypeCommand removeAnyByWeaponTypeCommand = new RemoveAnyByWeaponTypeCommand(collectionManager);
        commands.put(removeAnyByWeaponTypeCommand.getName(), removeAnyByWeaponTypeCommand);
        RemoveGreaterKeyCommand removeGreaterKeyCommand = new RemoveGreaterKeyCommand(collectionManager);
        commands.put(removeGreaterKeyCommand.getName(), removeGreaterKeyCommand);
        RemoveKeyCommand removeKeyCommand = new RemoveKeyCommand(collectionManager);
        commands.put(removeKeyCommand.getName(), removeKeyCommand);
        RemoveLowerKeyCommand removeLowerKeyCommand = new RemoveLowerKeyCommand(collectionManager);
        commands.put(removeLowerKeyCommand.getName(), removeLowerKeyCommand);
        ReplaceIfLowerCommand replaceIfLowerCommand = new ReplaceIfLowerCommand(collectionManager);
        commands.put(replaceIfLowerCommand.getName(), replaceIfLowerCommand);
        ShowCommand showCommand = new ShowCommand(collectionManager);
        commands.put(showCommand.getName(), showCommand);
        UpdateCommand updateCommand = new UpdateCommand(collectionManager);
        commands.put(updateCommand.getName(), updateCommand);
        ExecuteScriptCommand executeScriptCommand = new ExecuteScriptCommand();
        commands.put(executeScriptCommand.getName(), executeScriptCommand);
        HelpCommand helpCommand = new HelpCommand(commands);
        commands.put(helpCommand.getName(), helpCommand);

        commands.forEach((k, v) -> requirements.put(k, v.getRequirement()));
        saveCommand = new SaveCommand(collectionManager);
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

    public HashMap<String, CommandRequirement> getRequirements() {
        return requirements;
    }

    public SaveCommand getSaveCommand() {
        return saveCommand;
    }
}
