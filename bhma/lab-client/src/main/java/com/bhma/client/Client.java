package com.bhma.client;

import java.util.StringJoiner;

import javax.xml.bind.JAXBException;

import com.bhma.client.utility.CollectionCreator;
import com.bhma.client.utility.CollectionManager;
import com.bhma.client.utility.CommandManager;
import com.bhma.client.utility.ConsoleManager;
import com.bhma.client.utility.InputManager;
import com.bhma.client.utility.OutputManager;
import com.bhma.client.utility.SpaceMarineFiller;
import com.bhma.client.utility.SpaceMarineReader;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter the file path as a command line argument");
        } else {
            StringJoiner stringJoiner = new StringJoiner(" ");
            for (String arg : args) {
                stringJoiner.add(arg);
            }
            String filePath = stringJoiner.toString();
            try {
                CollectionManager collectionManager = CollectionCreator.load(filePath);
                OutputManager outputManager = new OutputManager(System.out);
                InputManager inputManager = new InputManager(System.in, outputManager);
                SpaceMarineFiller spaceMarineFiller = new SpaceMarineFiller(new SpaceMarineReader(inputManager),
                        inputManager, outputManager, collectionManager);
                CommandManager commandManager = new CommandManager(collectionManager, spaceMarineFiller, inputManager, outputManager);
                ConsoleManager consoleManager = new ConsoleManager(commandManager, inputManager, outputManager);
                consoleManager.start();
            } catch (JAXBException e) {
                System.out.println("Error during converting xml file " + filePath + " to java object.");
            }

        }
    }
}
