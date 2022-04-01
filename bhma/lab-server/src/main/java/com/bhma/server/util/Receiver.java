package com.bhma.server.util;

import com.bhma.common.exceptions.IllegalKeyException;
import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.exceptions.ScriptException;
import com.bhma.common.util.ClientRequest;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.Serializer;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.commands.Command;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Optional;

public class Receiver {
    private static final int PORT = 9990;
    private static final int BUFFER_SIZE = 2048;
    private CommandManager commandManager;
    private DatagramSocket server;

    public Receiver(CommandManager commandManager, DatagramSocket server) {
        this.commandManager = commandManager;
        this.server = server;
    }

    public void receive() throws IOException, ClassNotFoundException, InvalidInputException {
        byte[] buffer = new byte[BUFFER_SIZE];
        DatagramPacket request = new DatagramPacket(buffer, buffer.length);
        server.receive(request);
        ClientRequest clientRequest = (ClientRequest) Serializer.deserialize(buffer);
        InetAddress client = request.getAddress();
        int port = request.getPort();

        String inputCommand = clientRequest.getCommandName();
        String argument = clientRequest.getCommandArguments();
        Object objectArgument = clientRequest.getObjectArgument();
        ServerResponse response;
        Optional<Command> optional = commandManager.getCommands().stream().filter(v -> v.getName().equals(inputCommand)).findFirst();
        if (optional.isPresent()) {
            Command command = optional.get();
            try {
                response = command.execute(argument, objectArgument);
            } catch (ScriptException | InvalidCommandArguments | IllegalKeyException e) {
                response = new ServerResponse(e.getMessage(), ExecuteCode.ERROR);
            } catch (JAXBException e) {
                response = new ServerResponse("Error during converting data to file", ExecuteCode.ERROR);
            }
            byte[] buf = Serializer.serialize(response);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, client, port);
            server.send(packet);
        } else {
            response = new ServerResponse("Unknown command detected: " + inputCommand, ExecuteCode.ERROR);
            byte[] buf = Serializer.serialize(response);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, client, port);
            server.send(packet);
        }
    }
}
