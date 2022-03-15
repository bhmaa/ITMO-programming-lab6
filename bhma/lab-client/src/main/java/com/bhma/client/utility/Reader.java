package com.bhma.client.utility;

import com.bhma.client.exceptions.IllegalValueException;

@FunctionalInterface
public interface Reader<T> {
    T read() throws IllegalValueException, NumberFormatException, IllegalArgumentException;
}
