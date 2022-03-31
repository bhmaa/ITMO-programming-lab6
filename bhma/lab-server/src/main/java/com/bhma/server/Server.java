package com.bhma.server;

import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.server.util.CollectionCreator;
import com.bhma.server.util.CollectionManager;
import com.bhma.server.util.CommandManager;
import com.bhma.server.util.ExecuteManager;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.StringJoiner;

public final class Server {
    private static final int PORT = 9990;

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
                CollectionManager collectionManager = CollectionCreator.load(filename);
                DatagramChannel channel = DatagramChannel.open();
                channel.bind(new InetSocketAddress("127.0.0.1", PORT));
                CommandManager commandManager = new CommandManager(collectionManager);
                ExecuteManager executeManager = new ExecuteManager(commandManager, channel);
                executeManager.start();
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("wrong data");
            } catch (JAXBException | IOException e) {
                System.out.println("Error during converting xml " + filename + " to java object");
            }
        }
    }
}
