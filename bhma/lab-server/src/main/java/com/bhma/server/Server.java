package com.bhma.server;

import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionCreator;
import com.bhma.server.util.CollectionManager;
import com.bhma.server.util.CommandManager;
import com.bhma.server.util.ExecuteManager;
import com.bhma.server.util.InputManager;
import com.bhma.server.util.OutputManager;
import com.bhma.server.util.Sender;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.StringJoiner;

public final class Server {

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        OutputManager outputManager = new OutputManager(System.out);
        InputManager inputManager = new InputManager(System.in, outputManager);
        if (args.length == 0) {
            Sender.send(new ServerResponse("there's no file path in the command line argument on Server",
                    ExecuteCode.ERROR));
        } else {
            StringJoiner stringJoiner = new StringJoiner(" ");
            for (String arg : args) {
                stringJoiner.add(arg);
            }
            String filename = stringJoiner.toString();
            try {
                CollectionManager collectionManager = CollectionCreator.load(filename);
                CommandManager commandManager = new CommandManager(collectionManager);
                ExecuteManager executeManager = new ExecuteManager(commandManager, outputManager, inputManager);
                executeManager.start();
            } catch (JAXBException e) {
                Sender.send(new ServerResponse("Error during converting file " + filename + " to java object",
                        ExecuteCode.ERROR));
            } catch (InvalidInputException e) {
                Sender.send(new ServerResponse(e.getMessage(), ExecuteCode.ERROR));
            }
        }
    }
}
