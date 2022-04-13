package com.bhma.server.commands;

import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.util.CommandRequirement;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class SaveCommand extends Command {
    private final CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        super("save", "save changes to the xml file", CommandRequirement.NONE);
        this.collectionManager = collectionManager;
    }

    public ServerResponse execute(String argument, Object object) throws InvalidCommandArguments, JAXBException, IOException {
        if (!argument.isEmpty() || object != null) {
            throw new InvalidCommandArguments();
        }
        collectionManager.save();
        return new ServerResponse(ExecuteCode.SUCCESS);
    }
}
