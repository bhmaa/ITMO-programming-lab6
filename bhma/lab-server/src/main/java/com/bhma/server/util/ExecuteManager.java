package com.bhma.server.util;

import com.bhma.common.exceptions.IllegalKeyException;
import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.exceptions.ScriptException;
import com.bhma.common.util.ClientRequest;
import com.bhma.common.util.CommandRequirement;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.Serializer;
import com.bhma.common.util.ServerRequest;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.commands.Command;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Optional;

public class ExecuteManager {
    private static final int BUFFER_SIZE = 2048;
    private final CommandManager commandManager;
    private final DatagramChannel channel;

    public ExecuteManager(CommandManager commandManager, DatagramChannel channel) {
        this.commandManager = commandManager;
        this.channel = channel;
    }

    public void start() throws IOException, ClassNotFoundException, InvalidInputException {
        boolean executeFlag = true;
        while (executeFlag) {
            byte[] bytes = new byte[BUFFER_SIZE];
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            SocketAddress clientAddress = channel.receive(byteBuffer);
            ClientRequest clientRequest = (ClientRequest) Serializer.deserialize(bytes);
            String inputCommand = clientRequest.getCommandName();
            String argument = clientRequest.getCommandArguments();
            Optional<Command> optional = commandManager.getCommands().stream().filter(v -> v.getName().equals(inputCommand)).findFirst();
            if (optional.isPresent()) {
                Command command = optional.get();
                ServerResponse response;
                try {
                    if (command.getRequirement() == CommandRequirement.NONE) {
                        response = command.execute(argument);
                    } else {
                        ServerRequest serverRequest = new ServerRequest(command.getRequestMessage(), command.getRequirement());
                        bytes = Serializer.serialize(serverRequest);
                        byteBuffer = ByteBuffer.wrap(bytes);
                        channel.send(byteBuffer, clientAddress);
                        bytes = new byte[BUFFER_SIZE];
                        byteBuffer = ByteBuffer.wrap(bytes);
                        clientAddress = channel.receive(byteBuffer);
                        Object clientResponse = Serializer.deserialize(bytes);
                        response = command.execute(argument, clientResponse);
                    }
                    executeFlag = command.getExecuteFlag();
                } catch (ScriptException | InvalidCommandArguments | IllegalKeyException e) {
                    response = new ServerResponse(e.getMessage(), ExecuteCode.ERROR);
                } catch (JAXBException e) {
                    response = new ServerResponse("Error during converting data to file", ExecuteCode.ERROR);
                }
                bytes = Serializer.serialize(response);
            } else {
                ServerResponse response = new ServerResponse("Unknown command detected: " + inputCommand, ExecuteCode.ERROR);
                bytes = Serializer.serialize(response);
            }
            byteBuffer = ByteBuffer.wrap(bytes);
            channel.send(byteBuffer, clientAddress);
        }
    }
}
