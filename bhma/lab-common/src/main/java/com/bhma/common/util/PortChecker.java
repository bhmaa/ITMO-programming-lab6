package com.bhma.common.util;

import com.bhma.common.exceptions.IllegalPortException;

public final class PortChecker {
    private static final int MIN_PORT = 1;
    private static final int MAX_PORT = 65535;

    private PortChecker() {
    }

    public static int check(String str) throws IllegalPortException {
        try {
            final int port = Integer.parseInt(str);
            if (port < MIN_PORT || port > MAX_PORT) {
                throw new IllegalPortException("the port must be between 1 and 65535");
            } else {
                return port;
            }
        } catch (NumberFormatException e) {
            throw new IllegalPortException("the port must be a NUMBER!");
        }
    }
}
