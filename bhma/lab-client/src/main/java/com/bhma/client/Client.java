package com.bhma.client;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.util.StringJoiner;

import com.bhma.client.utility.ConsoleManager;
import com.bhma.client.utility.InputManager;
import com.bhma.client.utility.OutputManager;
import com.bhma.client.utility.Sender;
import com.bhma.client.utility.SpaceMarineFiller;
import com.bhma.client.utility.SpaceMarineReader;
import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.util.ClientRequest;
import com.bhma.client.utility.Color;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }
    public static void main(String[] args) {
        OutputManager outputManager = new OutputManager(System.out);
        InputManager inputManager = new InputManager(System.in, outputManager);

        try (DatagramChannel channel = Sender.start()) {
            // LOADING FILE WITH DATA FROM COMMAND LINE ARGUMENT
            ClientRequest request = new ClientRequest("","");
            Sender.send(channel, request);
            ServerResponse loadResponse = Sender.receiveResponse(channel);
            if (loadResponse.getExecuteCode().equals(ExecuteCode.SUCCESS)) {
                outputManager.printlnImportantColorMessage(loadResponse.getMessage(), Color.GREEN);

                SpaceMarineReader spaceMarineReader = new SpaceMarineReader(inputManager);
                SpaceMarineFiller spaceMarineFiller = new SpaceMarineFiller(spaceMarineReader, inputManager, outputManager);
                ConsoleManager consoleManager = new ConsoleManager(inputManager, outputManager, spaceMarineFiller, channel);
                try {
                    consoleManager.start();
                } catch (InvalidInputException e) {
                    outputManager.printlnImportantColorMessage(e.getMessage(), Color.RED);
                }
            } else {
                outputManager.printlnImportantColorMessage(loadResponse.getMessage(), Color.RED);
            }
        } catch (IOException | ClassNotFoundException e) {
            outputManager.printlnImportantColorMessage("error with server connection", Color.RED);
        }
    }
}
