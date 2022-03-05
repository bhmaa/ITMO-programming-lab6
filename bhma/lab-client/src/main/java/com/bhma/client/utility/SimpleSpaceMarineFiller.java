package com.bhma.client.utility;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.bhma.client.exceptions.IllegalValueException;
import com.bhma.client.exceptions.ScriptException;

public class SimpleSpaceMarineFiller<T> {
    private final OutputManager outputManager;
    private final SpaceMarineReader reader;
    private final InputManager inputManager;

    public SimpleSpaceMarineFiller(InputManager inputManager, OutputManager outputManager, SpaceMarineReader reader) {
        this.inputManager = inputManager;
        this.outputManager = outputManager;
        this.reader = reader;
    }
    /**
     * selection correct values of some SpaceMarine field
     * @param message message that will be written if input manager is not reading from a file now
     * @param readingMethod name of SpaceMarineReader's method that will be invoked
     * @return correct value of field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */

    public T fill(String message, String readingMethod) throws ScriptException {
        T returns;
        Method read;
        while (true) {
            try {
                read = reader.getClass().getMethod(readingMethod);
                outputManager.print(message + ": ");
                returns = (T) read.invoke(reader);
                break;
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof NumberFormatException) {
                    outputManager.println("Value must be a number");
                } else {
                    if (e.getCause() instanceof IllegalArgumentException) {
                        outputManager.println("Chose anything from list");
                    }
                    if (e.getCause() instanceof IllegalValueException) {
                        outputManager.println(e.getCause().getMessage());
                    }
                }
                if (inputManager.getScriptMode()) {
                    throw new ScriptException();
                }
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException e) {
                outputManager.println("Something went wrong...");
            }
        }
        return returns;
    }

}
