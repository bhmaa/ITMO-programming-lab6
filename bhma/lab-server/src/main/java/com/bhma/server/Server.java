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

public final class Server {
    private static final int BUFFER_SIZE = 2048;

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        if (args.length > 2) {
            try {
                // the port is indicated by the first word in the command line arguments
                final int port = PortChecker.check(args[0]);
                // the filename
                StringJoiner stringJoiner = new StringJoiner(" ");
                for (int i = 1; i < args.length; i++) {
                    stringJoiner.add(args[i]);
                }
                final String filename = stringJoiner.toString().trim();
                if (filename.isEmpty()) {
                    System.out.println("no data file!");
                } else {
                    try {
                        DatagramSocket server = new DatagramSocket(port);
                        CollectionManager collectionManager = CollectionCreator.load(filename);
                        CommandManager commandManager = new CommandManager(collectionManager);
                        Receiver receiver = new Receiver(commandManager, server, BUFFER_SIZE);
                        while (true) {
                            receiver.receive();
                        }
                    } catch (ClassNotFoundException e) {
                        System.out.println("wrong data");
                    } catch (JAXBException | IOException e) {
                        System.out.println("Error during converting xml " + filename + " to java object");
                    }
                }
            } catch (IllegalPortException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("no datafile and port!");
        }
    }
}
