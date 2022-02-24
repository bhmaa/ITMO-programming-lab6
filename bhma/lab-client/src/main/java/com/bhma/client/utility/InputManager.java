package com.bhma.client.utility;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Scanner;

public class InputManager {
    private Scanner defaultScanner;
    private Scanner fileScanner;
    private boolean scriptMode = false;
    private OutputManager outputManager;

    public InputManager(InputStream inputStream, OutputManager outputManager) {
        this.defaultScanner = new Scanner(inputStream);
        this.outputManager = outputManager;
    }

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

    public boolean getScriptMode() {
        return scriptMode;
    }

    public void finishReadScript() {
        scriptMode = false;
        outputManager.enableNotifications();
        outputManager.println("Reading from file was finished");
    }
}
