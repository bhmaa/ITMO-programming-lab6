package com.bhma.client.utility;

import com.bhma.client.exceptions.IllegalValueException;
import com.bhma.client.exceptions.InvalidInputException;

@FunctionalInterface
public interface Reader<T> {
    T read() throws IllegalValueException, NumberFormatException, IllegalArgumentException, InvalidInputException;
}
