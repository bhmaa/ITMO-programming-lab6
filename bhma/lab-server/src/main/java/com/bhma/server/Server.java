package com.bhma.server;

import com.bhma.common.exceptions.IllegalPortException;
import com.bhma.common.util.PortChecker;
import com.bhma.server.util.CollectionCreator;
import com.bhma.server.util.CollectionManager;
import com.bhma.server.util.CommandManager;
import com.bhma.server.util.Receiver;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.DatagramSocket;
import java.util.StringJoiner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public final class Server {
    private static final int BUFFER_SIZE = 2048;
    private static final Logger LOGGER = LogManager.getLogger(Server.class);

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        LOGGER.trace("the server is running");
        if (args.length > 1) {
            try {
                // the port is indicated by the first word in the command line arguments
                final int port = PortChecker.check(args[0]);
                LOGGER.info("set " + port + " port");
                // the filename
                StringJoiner stringJoiner = new StringJoiner(" ");
                for (int i = 1; i < args.length; i++) {
                    stringJoiner.add(args[i]);
                }
                final String filename = stringJoiner.toString().trim();
                if (filename.isEmpty()) {
                    LOGGER.error("no data file!");
                } else {
                    try {
                        DatagramSocket server = new DatagramSocket(port);
                        CollectionManager collectionManager = CollectionCreator.load(filename, LOGGER);
                        CommandManager commandManager = new CommandManager(collectionManager);
                        Receiver receiver = new Receiver(commandManager, server, BUFFER_SIZE, LOGGER);
                        while (true) {
                            receiver.receive();
                        }
                    } catch (ClassNotFoundException e) {
                        LOGGER.error("wrong data from client");
                    } catch (JAXBException | IOException e) {
                        LOGGER.error("Error during converting xml " + filename + " to java object");
                    }
                }
            } catch (IllegalPortException e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("no datafile and port!");
        }
        LOGGER.trace("the server is shutting down");
    }
}
