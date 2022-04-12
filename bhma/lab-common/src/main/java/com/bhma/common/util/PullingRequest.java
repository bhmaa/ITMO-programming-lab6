package com.bhma.common.util;

import java.io.Serializable;

public class PullingRequest implements Serializable {
    private final String commandName = "pull commands";

    @Override
    public String toString() {
        return "PullingRequest{"
                + " commandName='" + commandName + '\''
                + '}';
    }
}
