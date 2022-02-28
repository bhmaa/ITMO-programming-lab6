package com.bhma.client.utility;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * responsible for input
 */
public class InputManager {
    private final Scanner defaultScanner;
    private Scanner fileScanner;
    private boolean scriptMode = false;
    private final OutputManager outputManager;

    public InputManager(InputStream inputStream, OutputManager outputManager) {
        this.defaultScanner = new Scanner(inputStream);
        this.outputManager = outputManager;
    }

    /**
     * reads one line
     * @return the line that was read
     */
    public String read() {
        if (!scriptMode) {
            return defaultScanner.nextLine();
        } else {
            if (fileScanner.hasNext()) {
                return fileScanner.nextLine();
            } else {
                finishReadScript();
                outputManager.println("Reached the end of the file.");
                return defaultScanner.nextLine();
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
            fileScanner = new Scanner(Paths.get(fileName));
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
        scriptMode = false;
        outputManager.enableNotifications();
        outputManager.println("Reading from file was finished");
    }
}
