package com.bhma.server;

import com.bhma.common.exceptions.IllegalAddressException;
import com.bhma.common.util.Checker;
import com.bhma.server.util.CollectionCreator;
import com.bhma.server.util.CollectionManager;
import com.bhma.server.util.CommandManager;
import com.bhma.server.util.Receiver;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public final class Server {
    private static final int BUFFER_SIZE = 2048;
    private static final Logger LOGGER = LogManager.getLogger(Server.class);
    private static final int NUMBER_OF_ARGUMENTS = 3;

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        LOGGER.trace("the server is running");
        if (args.length == NUMBER_OF_ARGUMENTS) {
            try {
                //the host name is indicated by the first string in the command line arguments, the port - by second
                final InetSocketAddress address = Checker.checkAddress(args[0], args[1]);
                LOGGER.info(() -> "set " + address + " address");
                // the filename is indicated by the third string in the command line arguments
                final String filename = args[2].trim();
                if (filename.isEmpty()) {
                    LOGGER.error("no data file!");
                } else {
                    try {
                        DatagramSocket server = new DatagramSocket(address);
                        CollectionManager collectionManager = CollectionCreator.load(filename, LOGGER);
                        CommandManager commandManager = new CommandManager(collectionManager);
                        Receiver receiver = new Receiver(commandManager, server, BUFFER_SIZE, LOGGER);
                        while (true) {
                            receiver.receive();
                        }
                    } catch (ClassNotFoundException e) {
                        LOGGER.error("wrong data from client");
                    } catch (JAXBException | IOException e) {
                        LOGGER.error(() -> "Error during converting xml " + filename + " to java object", e);
                    }
                }
            } catch (IllegalAddressException e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("command line arguments must indicate host name, port and filename");
        }
        LOGGER.trace("the server is shutting down");
    }
}
