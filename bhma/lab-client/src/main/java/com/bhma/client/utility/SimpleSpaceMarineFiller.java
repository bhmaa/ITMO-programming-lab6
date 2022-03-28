package com.bhma.client.utility;

import com.bhma.client.exceptions.IllegalValueException;
import com.bhma.client.exceptions.InvalidInputException;
import com.bhma.client.exceptions.ScriptException;

public class SimpleSpaceMarineFiller {
    private final OutputManager outputManager;
    private final InputManager inputManager;

    public SimpleSpaceMarineFiller(InputManager inputManager, OutputManager outputManager) {
        this.inputManager = inputManager;
        this.outputManager = outputManager;
    }
    /**
     * selection correct values of some SpaceMarine field
     * @param message message that will be written if input manager is not reading from a file now
     * @param reader SpaceMarineReader's method that will be invoked or other lambda expression
     * @return correct value of field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */

    public <T> T fill(String message, Reader<T> reader) throws ScriptException, InvalidInputException {
        T returns;
        while (true) {
            try {
                outputManager.print(message + ": ");
                returns = reader.read();
                break;
            } catch (NumberFormatException e) {
                outputManager.println("Value must be a number");
                if (inputManager.getScriptMode()) {
                    throw new ScriptException();
                }
            } catch (IllegalArgumentException e) {
                outputManager.println("Chose anything from list");
                if (inputManager.getScriptMode()) {
                    throw new ScriptException();
                }
            } catch (IllegalValueException e) {
                outputManager.println(e.getMessage());
                if (inputManager.getScriptMode()) {
                    throw new ScriptException();
                }
            }
        }
        return returns;
    }

}
