package com.bhma.server.commands;

import com.bhma.common.data.Chapter;
import com.bhma.common.exceptions.InvalidCommandArguments;
import com.bhma.common.util.CommandRequirement;
import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerRequest;
import com.bhma.common.util.ServerResponse;
import com.bhma.server.util.CollectionManager;
import com.bhma.server.util.Sender;

import java.io.IOException;

/**
 * count_by_chapter command
 */
public class CountByChapterCommand extends Command {
    private final CollectionManager collectionManager;

    public CountByChapterCommand(CollectionManager collectionManager) {
        super("count_by_chapter", "вывести количество элементов, значение поля chapter которых равно заданному");
        this.collectionManager = collectionManager;
    }

    /**
     * print number of elements which have the same value of chapter field
     * @param argument must be empty
     * @throws InvalidCommandArguments if argument isn't empty
     */
    public void execute(String argument) throws InvalidCommandArguments, IOException, ClassNotFoundException {
        if (!argument.isEmpty()) {
            throw new InvalidCommandArguments();
        }
        Sender.send(new ServerRequest("server requests a chapter value...", CommandRequirement.CHAPTER));
        Chapter chapter = (Chapter) Sender.receiveObject();
        Sender.send(new ServerResponse(String.valueOf(collectionManager.countByChapter(chapter)), ExecuteCode.VALUE));
    }
}
