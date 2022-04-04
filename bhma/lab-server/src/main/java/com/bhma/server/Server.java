package com.bhma.server;

import com.bhma.server.util.CollectionCreator;
import com.bhma.server.util.CollectionManager;
import com.bhma.server.util.CommandManager;
import com.bhma.server.util.Receiver;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.DatagramSocket;
import java.util.StringJoiner;

public final class Server {
    private static final int PORT = 9990;
    private static final int BUFFER_SIZE = 2048;

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
        if (filename.trim().isEmpty()) {
            System.out.println("no data file");
        } else {
            try {
                DatagramSocket server = new DatagramSocket(PORT);
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
    }
}
