package com.bhma.client.utility;

import com.bhma.client.data.AstartesCategory;
import com.bhma.client.data.Chapter;
import com.bhma.client.data.Coordinates;
import com.bhma.client.data.MeleeWeapon;
import com.bhma.client.data.SpaceMarine;
import com.bhma.client.data.Weapon;
import com.bhma.client.exceptions.IllegalValueException;
import com.bhma.client.exceptions.ScriptException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SpaceMarineFiller<T> {
    private final SpaceMarineReader reader;
    private final InputManager inputManager;
    private final OutputManager outputManager;
    private final CollectionManager collectionManager;

    public SpaceMarineFiller(SpaceMarineReader reader, InputManager inputManager,
                             OutputManager outputManager, CollectionManager collectionManager) {
        this.reader = reader;
        this.inputManager = inputManager;
        this.outputManager = outputManager;
        this.collectionManager = collectionManager;
    }
    public T fill(String message, Method read) throws ScriptException {
        T returns;
        while (true) {
            try {
                outputManager.print(message + ": ");
                returns = (T) read.invoke(reader);
                break;
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof NumberFormatException) {
                    outputManager.println("Value must be a number");
                } else {
                    if (e.getCause() instanceof IllegalArgumentException) {
                        outputManager.println("Chose anything from list");
                    }
                    if (e.getCause() instanceof IllegalValueException) {
                        outputManager.println(e.getCause().getMessage());
                    }
                }
                if (inputManager.getScriptMode()) {
                    throw new ScriptException();
                }
            } catch (IllegalAccessException e) {
                outputManager.println("Something went wrong...");
            }
        }
        return returns;
    }

    public String fillName() throws ScriptException {
        String name = null;
        try {
            name = (String) fill("Enter name", SpaceMarineReader.class.getMethod("readNotNullString"));
        } catch (NoSuchMethodException e) {
            outputManager.println("Something went wrong...");
        }
        return name;
    }

    public double fillX() throws ScriptException {
        Double x = null;
        try {
            x = (double) fill("Enter x coordinate", SpaceMarineReader.class.getMethod("readX"));
        } catch (NoSuchMethodException e) {
            outputManager.println("Something went wrong...");
        }
        return x;
    }

    public long fillY() throws ScriptException {
        Long y = null;
        try {
            y = (long) fill("Enter y coordinate", SpaceMarineReader.class.getMethod("readY"));
        } catch (NoSuchMethodException e) {
            outputManager.println("Something went wrong...");
        }
        return y;
    }

    public Coordinates fillCoordinates() throws ScriptException {
        return new Coordinates(this.fillX(), this.fillY());
    }

    public Double fillHealth() throws ScriptException {
        Double health = null;
        try {
            health = (Double) fill("Enter health", SpaceMarineReader.class.getMethod("readHealth"));
        } catch (NoSuchMethodException e) {
            outputManager.println("Something went wrong...");
        }
        return health;
    }

    public AstartesCategory fillCategory() throws ScriptException {
        AstartesCategory category = null;
        try {
            category = (AstartesCategory) fill("Chose the astartes category. Type SCOUT, INCEPTOR, TACTICAL or CHAPLAIN",
                    SpaceMarineReader.class.getMethod("readCategory"));
        } catch (NoSuchMethodException e) {
            outputManager.println("Something went wrong...");
        }
        return category;
    }

    public Weapon fillWeaponType() throws ScriptException {
        Weapon weapon = null;
        try {
            weapon = (Weapon) fill("Chose the weapon type. Type HEAVY_BOLTGUN, BOLT_RIFLE, PLASMA_GUN or INFERNO_PISTOL",
                    SpaceMarineReader.class.getMethod("readWeaponType"));
        } catch (NoSuchMethodException e) {
            outputManager.println("Something went wrong...");
        }
        return weapon;
    }

    public MeleeWeapon fillMeleeWeapon() throws ScriptException {
        MeleeWeapon meleeWeapon = null;
        try {
            meleeWeapon = (MeleeWeapon) fill("Chose the melee weapon. Type CHAIN_AXE, MANREAPER, LIGHTING_CLAW, POWER_BLADE or POWER_FIST",
                    SpaceMarineReader.class.getMethod("readMeleeWeapon"));
        } catch (NoSuchMethodException e) {
            outputManager.println("Something went wrong...");
        }
        return meleeWeapon;
    }

    public String fillChapterName() throws ScriptException {
        String chapterName = null;
        try {
            chapterName = (String) fill("Enter chapter's name", SpaceMarineReader.class.getMethod("readNotNullString"));
        } catch (NoSuchMethodException e) {
            outputManager.println("Something went wrong...");
        }
        return chapterName;
    }

    public String fillChapterWorld() throws ScriptException {
        String chapterWorld = null;
        try {
            chapterWorld = (String) fill("Enter chapter's world", SpaceMarineReader.class.getMethod("readNotNullString"));
        } catch (NoSuchMethodException e) {
            outputManager.println("Something went wrong...");
        }
        return chapterWorld;
    }

    public Chapter fillChapter() throws ScriptException {
        return new Chapter(this.fillChapterName(), this.fillChapterWorld());
    }

    public SpaceMarine fillSpaceMarine() throws ScriptException {
        return new SpaceMarine(this.fillName(), this.fillCoordinates(), this.fillHealth(),
                this.fillCategory(), this.fillWeaponType(), this.fillMeleeWeapon(), this.fillChapter(), collectionManager);
    }
}
