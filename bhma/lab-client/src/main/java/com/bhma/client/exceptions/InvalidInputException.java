package com.bhma.client.exceptions;

/**
 * throws if entered value was ctrl+d
 */
public class InvalidInputException extends Exception {
    public String getMessage() {
        return "Invalid input. Work with the collection will be finished";
    }
}
