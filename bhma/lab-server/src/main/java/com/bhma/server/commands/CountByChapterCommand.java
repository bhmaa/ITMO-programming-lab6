package com.bhma.server.commands;

import com.bhma.common.data.Chapter;
import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.util.CommandRequirement;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;

/**
 * count_by_chapter command
 */
public class CountByChapterCommand extends Command {
    private final CollectionManager collectionManager;

    public CountByChapterCommand(CollectionManager collectionManager) {
        super("count_by_chapter", "вывести количество элементов, значение поля chapter которых равно заданному",
                CommandRequirement.CHAPTER, "server requests a chapter value...");
        this.collectionManager = collectionManager;
    }

    /**
     * print number of elements which have the same value of chapter field
     * @param argument must be empty
     * @throws InvalidCommandArguments if argument isn't empty
     */
    public ServerResponse execute(String argument, Object object) throws InvalidCommandArguments {
        if (!argument.isEmpty() || object == null) {
            throw new InvalidCommandArguments();
        }
        return new ServerResponse(String.valueOf(collectionManager.countByChapter((Chapter) object)), ExecuteCode.VALUE);
    }

    public ServerResponse execute(String argument) throws InvalidCommandArguments {
        throw new InvalidCommandArguments();
    }
}
