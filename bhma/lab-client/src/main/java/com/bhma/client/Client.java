package com.bhma.client;

import java.io.IOException;
import java.nio.channels.DatagramChannel;

import com.bhma.client.utility.ConsoleManager;
import com.bhma.client.utility.InputManager;
import com.bhma.client.utility.OutputManager;
import com.bhma.client.utility.Sender;
import com.bhma.client.utility.SpaceMarineFiller;
import com.bhma.client.utility.SpaceMarineReader;
import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.client.utility.Color;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        OutputManager outputManager = new OutputManager(System.out);
        InputManager inputManager = new InputManager(System.in, outputManager);

        try (DatagramChannel channel = Sender.start()) {
            SpaceMarineReader spaceMarineReader = new SpaceMarineReader(inputManager);
            SpaceMarineFiller spaceMarineFiller = new SpaceMarineFiller(spaceMarineReader, inputManager, outputManager);
            ConsoleManager consoleManager = new ConsoleManager(inputManager, outputManager, spaceMarineFiller, channel);
            try {
                consoleManager.start();
            } catch (InvalidInputException e) {
                outputManager.printlnImportantColorMessage(e.getMessage(), Color.RED);
            }
        } catch (IOException | ClassNotFoundException e) {
            outputManager.printlnImportantColorMessage("error with server connection", Color.RED);
        }
    }
}
