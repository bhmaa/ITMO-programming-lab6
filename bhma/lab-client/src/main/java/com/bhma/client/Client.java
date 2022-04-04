package com.bhma.client;

import java.io.IOException;
import java.util.HashMap;

import com.bhma.client.utility.Color;
import com.bhma.client.utility.ConsoleManager;
import com.bhma.client.utility.InputManager;
import com.bhma.client.utility.OutputManager;
import com.bhma.client.utility.Requester;
import com.bhma.client.utility.SpaceMarineFiller;
import com.bhma.client.utility.SpaceMarineReader;
import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.exceptions.NoConnectionException;
import com.bhma.common.util.CommandRequirement;

public final class Client {
    private static final int SERVER_PORT = 9990;
    private static final int TIMEOUT = 1000;
    private static final int BUFFER_SIZE = 3048;
    private static final int RECONNECTION_ATTEMPTS = 5;

    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        HashMap<String, CommandRequirement> commands = new HashMap<>();
        commands.put("average_of_health", CommandRequirement.NONE);
        commands.put("clear", CommandRequirement.NONE);
        commands.put("count_by_chapter", CommandRequirement.CHAPTER);
        commands.put("execute_script", CommandRequirement.NONE);
        commands.put("help", CommandRequirement.NONE);
        commands.put("info", CommandRequirement.NONE);
        commands.put("insert", CommandRequirement.SPACE_MARINE);
        commands.put("remove_any_by_weapon_type", CommandRequirement.WEAPON);
        commands.put("remove_greater_key", CommandRequirement.NONE);
        commands.put("remove_key", CommandRequirement.NONE);
        commands.put("remove_lower_key", CommandRequirement.NONE);
        commands.put("replace_if_lower", CommandRequirement.SPACE_MARINE);
        commands.put("show", CommandRequirement.NONE);
        commands.put("update", CommandRequirement.SPACE_MARINE);

        OutputManager outputManager = new OutputManager(System.out);
        InputManager inputManager = new InputManager(System.in, outputManager);
        Requester requester = new Requester(SERVER_PORT, TIMEOUT, BUFFER_SIZE, RECONNECTION_ATTEMPTS, outputManager);
        SpaceMarineReader spaceMarineReader = new SpaceMarineReader(inputManager);
        SpaceMarineFiller spaceMarineFiller = new SpaceMarineFiller(spaceMarineReader, inputManager, outputManager);
        ConsoleManager consoleManager = new ConsoleManager(commands, inputManager, outputManager, spaceMarineFiller, requester);
        try {
            consoleManager.start();
        } catch (InvalidInputException e) {
            outputManager.printlnImportantColorMessage(e.getMessage(), Color.RED);
        } catch (NoConnectionException e) {
            outputManager.printlnImportantColorMessage(e.getMessage(), Color.RED);
        } catch (IOException | ClassNotFoundException e) {
            outputManager.printlnImportantColorMessage("error with server connection", Color.RED);
        }
    }
}
