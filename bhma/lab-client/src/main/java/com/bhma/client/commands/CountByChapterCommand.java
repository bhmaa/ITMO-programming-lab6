package com.bhma.client.commands;

import com.bhma.client.exceptions.InvalidInputException;
import com.bhma.client.exceptions.NoSuchCommandException;
import com.bhma.client.exceptions.ScriptException;
import com.bhma.client.utility.CollectionManager;
import com.bhma.client.utility.OutputManager;
import com.bhma.client.utility.SpaceMarineFiller;

/**
 * count_by_chapter command
 */
public class CountByChapterCommand extends Command {
    private final CollectionManager collectionManager;
    private final SpaceMarineFiller spaceMarineFiller;
    private final OutputManager outputManager;

    public CountByChapterCommand(CollectionManager collectionManager, SpaceMarineFiller spaceMarineFiller, OutputManager outputManager) {
        super("count_by_chapter", "вывести количество элементов, значение поля chapter которых равно заданному");
        this.collectionManager = collectionManager;
        this.spaceMarineFiller = spaceMarineFiller;
        this.outputManager = outputManager;
    }

    /**
     * print number of elements which have the same value of chapter field
     * @param argument must be empty
     * @throws ScriptException while script execution was entered invalid value of chapter
     * @throws NoSuchCommandException if argument isn't empty
     */
    public void execute(String argument) throws ScriptException, NoSuchCommandException, InvalidInputException {
        if (!argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        outputManager.println(String.valueOf(collectionManager.countByChapter(spaceMarineFiller.fillChapter())));
    }
}
