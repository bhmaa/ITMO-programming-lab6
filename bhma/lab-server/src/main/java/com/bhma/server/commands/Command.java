package com.bhma.server.commands;

import com.bhma.common.exceptions.IllegalKeyException;
import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.exceptions.ScriptException;
import com.bhma.common.util.ServerResponse;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * parent of all commands
 */
public abstract class Command {
    private final String name;
    private final String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract ServerResponse execute(String argument, Object object) throws InvalidCommandArguments,
            ScriptException, IllegalKeyException, IOException, ClassNotFoundException, JAXBException;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
