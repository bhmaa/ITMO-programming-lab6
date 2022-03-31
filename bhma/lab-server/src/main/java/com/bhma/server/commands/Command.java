package com.bhma.server.commands;

import com.bhma.common.exceptions.IllegalKeyException;
import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.exceptions.InvalidInputException;
import com.bhma.common.exceptions.ScriptException;
import com.bhma.common.util.CommandRequirement;
import com.bhma.common.util.ServerResponse;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * parent of all commands
 */
public abstract class Command {
    private final String name;
    private final String description;
    private boolean executeFlag = true;
    private final CommandRequirement requirement;
    private final String requestMessage;

    public Command(String name, String description, CommandRequirement requirement) {
        this.name = name;
        this.description = description;
        this.requirement = requirement;
        requestMessage = "";
    }

    public Command(String name, String description, CommandRequirement requirement, String requestMessage) {
        this.name = name;
        this.description = description;
        this.requirement = requirement;
        this.requestMessage = requestMessage;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public CommandRequirement getRequirement() {
        return requirement;
    }

    /**
     * NOT OVERRIDEN METHOD WILL THROW A NEW INVALIDCOMMANDARGUMENTS JUST TO NOT EXECUTE COMMAND IF IT ALSO TAKES AN OBJECT ARGUMENT
     * @param argument
     * @return
     * @throws InvalidCommandArguments
     * @throws ScriptException
     * @throws IllegalKeyException
     * @throws InvalidInputException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ServerResponse execute(String argument) throws InvalidCommandArguments,
            ScriptException, IllegalKeyException, InvalidInputException, IOException, ClassNotFoundException, JAXBException {
        throw new InvalidCommandArguments();
    }
    /**
     * NOT OVERRIDEN METHOD WILL THROW A NEW INVALIDCOMMANDARGUMENTS JUST TO NOT EXECUTE COMMAND IF IT DOESN'T TAKE AN OBJECT ARGUMENT
     * @param argument
     * @param object
     * @return
     * @throws InvalidCommandArguments
     * @throws ScriptException
     * @throws IllegalKeyException
     * @throws InvalidInputException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ServerResponse execute(String argument, Object object) throws InvalidCommandArguments,
            ScriptException, IllegalKeyException, InvalidInputException, IOException, ClassNotFoundException {
        throw new InvalidCommandArguments();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getExecuteFlag() {
        return executeFlag;
    }

    /**
     * @param executeFlag false if it is an exit command and true if it is not
     */
    public void setExecuteFlag(boolean executeFlag) {
        this.executeFlag = executeFlag;
    }
}
