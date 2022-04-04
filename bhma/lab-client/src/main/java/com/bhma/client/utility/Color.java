package com.bhma.client.utility;

public enum Color {
    RED("\u001B[31m"),
    GREEN("\u001B[32m");

    private final String code;

    Color(String code) {
        this.code = code;
    }

    public String toString() {
        return code;
    }

    public String colorize(String message) {
        return this.toString() + message + "\u001B[0m";
    }
}
