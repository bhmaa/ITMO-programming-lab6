package com.bhma.client.utility;

import java.io.IOException;
import java.io.OutputStream;

public class OutputManager {
    private final OutputStream outputStream;
    private MessageNotifications messageNotifications = MessageNotifications.ON;

    private enum MessageNotifications {
        ON,
        OFF;
    }

    public OutputManager(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void println(String string) {
        try {
            if (messageNotifications.equals(MessageNotifications.ON)) {
                outputStream.write(string.getBytes());
                outputStream.write("\n".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print(String string) {
        try {
            if (messageNotifications.equals(MessageNotifications.ON)) {
                outputStream.write(string.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void muteNotifications() {
        messageNotifications = MessageNotifications.OFF;
    }

    public void enableNotifications() {
        messageNotifications = MessageNotifications.ON;
    }
}
