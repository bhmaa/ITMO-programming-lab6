package com.bhma.client.utility;

import java.io.IOException;
import java.io.OutputStream;

/**
 * responsible for output
 */
public class OutputManager {
    private final OutputStream outputStream;
    private MessageNotifications messageNotifications = MessageNotifications.ON;

    private enum MessageNotifications {
        ON,
        OFF
    }

    public OutputManager(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * writes a string with a new string symbol in the end to the output stream that set in the constructor if notification is on
     * @param string
     */
    public void println(String string) {
        if (messageNotifications.equals(MessageNotifications.ON)) {
            printlnImportantMessage(string);
        }
    }

    /**
     * writes a string with a new string symbol in the end to the output stream that set in the constructor even if notification is off
     * @param string
     */
    public void printlnImportantMessage(String string) {
        try {
            outputStream.write(string.getBytes());
            outputStream.write("\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * writes string with a red color
     * @param string
     */
    public void printlnImportantWarning(String string) {
        try {
            outputStream.write("\u001B[31m".getBytes());
            printlnImportantMessage(string);
            outputStream.write("\u001B[0m".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * writes string with a red color if notification is on
     * @param string
     */
    public void printlnWarning(String string) {
        if (messageNotifications.equals(MessageNotifications.ON)) {
            printlnImportantWarning(string);
        }
    }

    /**
     * writes a string to the output stream that set in the constructor if notification is on
     * @param string
     */
    public void print(String string) {
        try {
            if (messageNotifications.equals(MessageNotifications.ON)) {
                outputStream.write(string.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printLnSuccessMessage(String string) {
        if (messageNotifications.equals(MessageNotifications.ON)) {
            try {
                outputStream.write("\u001B[32m".getBytes());
                printlnImportantMessage(string);
                outputStream.write("\u001B[0m".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * turn off notification; messages will be not written until you turn on notification
     */
    public void muteNotifications() {
        messageNotifications = MessageNotifications.OFF;
    }

    /**
     * turn on notification
     */
    public void enableNotifications() {
        messageNotifications = MessageNotifications.ON;
    }
}
