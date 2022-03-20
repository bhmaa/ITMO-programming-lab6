package com.bhma.client.utility;

import com.bhma.client.exceptions.InvalidInputException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Stack;

/**
 * responsible for input
 */
public class InputManager {
    private Stack<Scanner> scanners = new Stack<>();
    private boolean scriptMode = false;
    private final OutputManager outputManager;

    public InputManager(InputStream inputStream, OutputManager outputManager) {
        scanners.push(new Scanner(inputStream));
        this.outputManager = outputManager;
    }

    /**
     * reads one line
     * @return the line that was read
     */
    public String read() throws InvalidInputException {
        if (scanners.peek().hasNextLine()) {
            return scanners.peek().nextLine();
        } else {
            if (scriptMode) {
                finishReadScript();
                outputManager.println("Reached the end of the file.");
                return read();
            }  else {
                throw new InvalidInputException();
            }
        }
    }

    /**
     * starts read from the file
     * @param fileName
     */
    public void startReadScript(String fileName) {
        try {
            outputManager.println("Start reading from file " + fileName + "...");
            scanners.push(new Scanner(Paths.get(fileName)));
            scriptMode = true;
            outputManager.muteNotifications();
        } catch (IOException e) {
            outputManager.println("Cannot find file with this name");
        }
    }

    /**
     * @return true if input manager is reading from file now, and false otherwise
     */
    public boolean getScriptMode() {
        return scriptMode;
    }

    /**
     * finish read from the file and starts read from input stream that set in the constructor
     */
    public void finishReadScript() {
        if(scanners.size() == 2) {
            scriptMode = false;
            outputManager.enableNotifications();
        }
        scanners.pop();
        outputManager.println("Reading from file was finished");
    }
}
