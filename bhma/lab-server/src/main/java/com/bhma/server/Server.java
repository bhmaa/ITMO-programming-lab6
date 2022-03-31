package com.bhma.server;

import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.util.ClientRequest;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionCreator;
import com.bhma.server.util.CollectionManager;
import com.bhma.server.util.CommandManager;
import com.bhma.server.util.ExecuteManager;
import com.bhma.server.util.Sender;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.util.StringJoiner;

public final class Server {

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        // LOAD FROM COMMAND LINE ARGS
        StringJoiner stringJoiner = new StringJoiner(" ");
        for (String arg : args) {
            stringJoiner.add(arg);
        }
        String filename = stringJoiner.toString();

        while (true) {
            try (DatagramChannel channel = Sender.start()) {
                ClientRequest clientRequest = Sender.receiveRequest(channel);
                if (!filename.trim().isEmpty()) {
                    try {
                        CollectionManager collectionManager = CollectionCreator.load(filename, channel);
                        CommandManager commandManager = new CommandManager(collectionManager, channel);
                        ExecuteManager executeManager = new ExecuteManager(commandManager, channel);
                        executeManager.start();
                    } catch (JAXBException e) {
                        Sender.send(channel, new ServerResponse("Error during converting file " + filename
                                + " to java object", ExecuteCode.ERROR));
                    } catch (InvalidInputException e) {
                        e.printStackTrace();
                    }
                } else {
                    Sender.send(channel, new ServerResponse("there's no filepath in server",
                            ExecuteCode.ERROR));
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
