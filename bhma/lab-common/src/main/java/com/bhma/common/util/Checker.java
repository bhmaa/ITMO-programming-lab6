package com.bhma.common.util;

import com.bhma.common.exceptions.IllegalAddressException;

import java.net.InetSocketAddress;

public final class Checker {
    private static final int MIN_PORT = 1;
    private static final int MAX_PORT = 65535;

    private Checker() {
    }

    private static int checkPort(String str) throws IllegalAddressException {
        try {
            final int port = Integer.parseInt(str);
            if (port < MIN_PORT || port > MAX_PORT) {
                throw new IllegalAddressException("the port must be between 1 and 65535");
            } else {
                return port;
            }
        } catch (NumberFormatException e) {
            throw new IllegalAddressException("the port must be a NUMBER!");
        }
    }

    public static InetSocketAddress checkAddress(String host, String port) throws IllegalAddressException {
        InetSocketAddress address = new InetSocketAddress(host, checkPort(port));
        if (address.isUnresolved()) {
            throw new IllegalAddressException("the address is unresolved. check the host name");
        }
        return address;
    }
}
