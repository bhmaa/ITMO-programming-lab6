package com.bhma.common.util;

import java.io.Serializable;
import java.util.HashMap;

public class PullingResponse implements Serializable {
    private final HashMap<String, CommandRequirement> requirements;

    public PullingResponse(HashMap<String, CommandRequirement> requirements) {
        this.requirements = requirements;
    }

    public HashMap<String, CommandRequirement> getRequirements() {
        return requirements;
    }

    @Override
    public String toString() {
        return "PullingResponse{"
                + " requirements=" + requirements
                + '}';
    }
}
