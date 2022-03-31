package com.bhma.server;

import com.bhma.common.exceptions.InvalidInputException;
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

public final class Server {

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvalidInputException {
        DatagramChannel channel = Sender.start();
        String filename = Sender.receiveRequest(channel).getCommandName();
        if (!filename.trim().isEmpty()) {
            try {
                CollectionManager collectionManager = CollectionCreator.load(filename, channel);
                CommandManager commandManager = new CommandManager(collectionManager, channel);
                ExecuteManager executeManager = new ExecuteManager(commandManager, channel);
                executeManager.start();
            } catch (JAXBException e) {
                Sender.send(channel, new ServerResponse("Error during converting file " + filename
                        + " to java object", ExecuteCode.ERROR));
            }
        } else {
            Sender.send(channel, new ServerResponse("there's no filepath in a command line arguments",
                    ExecuteCode.ERROR));
        }

        channel.close();
    }
}
