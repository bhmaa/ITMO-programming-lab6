package com.bhma.client;

import java.util.StringJoiner;

import javax.xml.bind.JAXBException;

import com.bhma.client.exceptions.InvalidInputException;
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
        OutputManager outputManager = new OutputManager(System.out);
        InputManager inputManager = new InputManager(System.in, outputManager);
        if (args.length == 0) {
            outputManager.printlnImportantMessage("Please enter the file path as a command line argument");
        } else {
            StringJoiner filepath = new StringJoiner(" ");
            for (String arg : args) {
                filepath.add(arg);
            }
            try {
                CollectionManager collectionManager = CollectionCreator.load(filepath.toString(), outputManager);
                SpaceMarineFiller spaceMarineFiller = new SpaceMarineFiller(new SpaceMarineReader(inputManager),
                        inputManager, outputManager, collectionManager);
                CommandManager commandManager = new CommandManager(collectionManager, spaceMarineFiller, inputManager, outputManager);
                ConsoleManager consoleManager = new ConsoleManager(commandManager, inputManager, outputManager);
                consoleManager.start();
            } catch (JAXBException e) {
                outputManager.printlnImportantMessage("Error during converting xml file " + filepath.toString() + " to java object.");
            } catch (InvalidInputException e) {
                outputManager.printlnImportantMessage(e.getMessage());
            }

        }
    }
}
