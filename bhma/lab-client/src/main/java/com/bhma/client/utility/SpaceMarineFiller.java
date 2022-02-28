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

/**
 * responsible for the selection correct values of SpaceMarine fields
 */
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

    /**
     * selection correct values of some SpaceMarine field
     * @param message message that will be written if input manager is not reading from a file now
     * @param read method of SpaceMarineReader that will be invoked
     * @return correct value of field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
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

    /**
     * selection correct values of name field in SpaceMarine
     * @return correct value of name field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public String fillName() throws ScriptException {
        String name = null;
        try {
            name = (String) fill("Enter name", SpaceMarineReader.class.getMethod("readNotNullString"));
        } catch (NoSuchMethodException e) {
            outputManager.println("Something went wrong...");
        }
        return name;
    }

    /**
     * selection correct values of x field in Coordinates
     * @return correct value of x field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public double fillX() throws ScriptException {
        Double x = null;
        try {
            x = (double) fill("Enter x coordinate", SpaceMarineReader.class.getMethod("readX"));
        } catch (NoSuchMethodException e) {
            outputManager.println("Something went wrong...");
        }
        return x;
    }

    /**
     * selection correct values of y field in Coordinates
     * @return correct value of y field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public long fillY() throws ScriptException {
        Long y = null;
        try {
            y = (long) fill("Enter y coordinate", SpaceMarineReader.class.getMethod("readY"));
        } catch (NoSuchMethodException e) {
            outputManager.println("Something went wrong...");
        }
        return y;
    }

    /**
     * selection correct values of coordinates field in SpaceMarine
     * @return correct value of coordinates field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public Coordinates fillCoordinates() throws ScriptException {
        return new Coordinates(this.fillX(), this.fillY());
    }

    /**
     * selection correct values of health field in SpaceMarine
     * @return correct value of health field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public Double fillHealth() throws ScriptException {
        Double health = null;
        try {
            health = (Double) fill("Enter health", SpaceMarineReader.class.getMethod("readHealth"));
        } catch (NoSuchMethodException e) {
            outputManager.println("Something went wrong...");
        }
        return health;
    }

    /**
     * selection correct values of category field in SpaceMarine
     * @return correct value of category field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
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

    /**
     * selection correct values of weaponType field in SpaceMarine
     * @return correct value of weaponType field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
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

    /**
     * selection correct values of meleeWeapon field in SpaceMarine
     * @return correct value of meleeWeapon field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
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

    /**
     * selection correct values of name field in Chapter
     * @return correct value of name field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public String fillChapterName() throws ScriptException {
        String chapterName = null;
        try {
            chapterName = (String) fill("Enter chapter's name", SpaceMarineReader.class.getMethod("readNotNullString"));
        } catch (NoSuchMethodException e) {
            outputManager.println("Something went wrong...");
        }
        return chapterName;
    }

    /**
     * selection correct values of world field in Chapter
     * @return correct value of world field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public String fillChapterWorld() throws ScriptException {
        String chapterWorld = null;
        try {
            chapterWorld = (String) fill("Enter chapter's world", SpaceMarineReader.class.getMethod("readNotNullString"));
        } catch (NoSuchMethodException e) {
            outputManager.println("Something went wrong...");
        }
        return chapterWorld;
    }

    /**
     * selection correct values of chapter field in SpaceMarine
     * @return correct value of chapter field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public Chapter fillChapter() throws ScriptException {
        return new Chapter(this.fillChapterName(), this.fillChapterWorld());
    }

    /**
     * selection correct values of all fields in SpaceMarine
     * @return new object with entered values
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public SpaceMarine fillSpaceMarine() throws ScriptException {
        return new SpaceMarine(this.fillName(), this.fillCoordinates(), this.fillHealth(),
                this.fillCategory(), this.fillWeaponType(), this.fillMeleeWeapon(), this.fillChapter(), collectionManager);
    }
}
