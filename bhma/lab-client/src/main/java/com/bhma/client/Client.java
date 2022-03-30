package com.bhma.client;

import java.io.IOException;

import com.bhma.client.utility.ConsoleManager;
import com.bhma.client.utility.InputManager;
import com.bhma.client.utility.OutputManager;
import com.bhma.client.utility.Sender;
import com.bhma.client.utility.SpaceMarineFiller;
import com.bhma.client.utility.SpaceMarineReader;
import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.util.Color;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        OutputManager outputManager = new OutputManager(System.out);
        InputManager inputManager = new InputManager(System.in, outputManager);
        SpaceMarineReader spaceMarineReader = new SpaceMarineReader(inputManager);
        SpaceMarineFiller spaceMarineFiller = new SpaceMarineFiller(spaceMarineReader, inputManager, outputManager);
        ConsoleManager consoleManager = new ConsoleManager(inputManager, outputManager, spaceMarineFiller);

        //checks for loading
        ServerResponse loadResponse = (ServerResponse) Sender.receiveObject();
        if (loadResponse.getExecuteCode().equals(ExecuteCode.SUCCESS)) {
            outputManager.printlnImportantColorMessage(loadResponse.getMessage(), Color.GREEN);
            try {
                consoleManager.start();
            } catch (InvalidInputException e) {
                outputManager.printlnImportantColorMessage(e.getMessage(), Color.RED);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            outputManager.printlnImportantColorMessage(loadResponse.getMessage(), Color.RED);
        }
    }
}
