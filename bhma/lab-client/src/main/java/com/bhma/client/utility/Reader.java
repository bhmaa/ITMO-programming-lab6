package com.bhma.client.utility;

import com.bhma.common.exceptions.IllegalValueException;
import com.bhma.common.exceptions.InvalidInputException;

@FunctionalInterface
public interface Reader<T> {
    T read() throws IllegalValueException, IllegalArgumentException, InvalidInputException;
}
