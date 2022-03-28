package com.bhma.client.utility;

import com.bhma.client.data.AstartesCategory;
import com.bhma.client.data.Chapter;
import com.bhma.client.data.Coordinates;
import com.bhma.client.data.MeleeWeapon;
import com.bhma.client.data.SpaceMarine;
import com.bhma.client.data.Weapon;
import com.bhma.client.exceptions.InvalidInputException;
import com.bhma.client.exceptions.ScriptException;

/**
 * responsible for the selection correct values of SpaceMarine fields
 */
public class SpaceMarineFiller {
    private final SpaceMarineReader reader;
    private final InputManager inputManager;
    private final OutputManager outputManager;
    private final CollectionManager collectionManager;
    private final SimpleSpaceMarineFiller simpleSpaceMarineFiller;

    public SpaceMarineFiller(SpaceMarineReader reader, InputManager inputManager,
                             OutputManager outputManager, CollectionManager collectionManager) {
        this.reader = reader;
        this.inputManager = inputManager;
        this.outputManager = outputManager;
        this.collectionManager = collectionManager;
        simpleSpaceMarineFiller = new SimpleSpaceMarineFiller(inputManager, outputManager);
    }

    /**
     * selection correct values of name field in SpaceMarine
     * @return correct value of name field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public String fillName() throws ScriptException, InvalidInputException {
        return simpleSpaceMarineFiller.fill("Enter name", reader::readNotEmptyString);
    }

    /**
     * selection correct values of x field in Coordinates
     * @return correct value of x field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public double fillX() throws ScriptException, InvalidInputException {
        return simpleSpaceMarineFiller.fill("Enter x coordinate", reader::readX);
    }

    /**
     * selection correct values of y field in Coordinates
     * @return correct value of y field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public long fillY() throws ScriptException, InvalidInputException {
        return simpleSpaceMarineFiller.fill("Enter y coordinate", reader::readY);
    }

    /**
     * selection correct values of coordinates field in SpaceMarine
     * @return correct value of coordinates field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public Coordinates fillCoordinates() throws ScriptException, InvalidInputException {
        return new Coordinates(this.fillX(), this.fillY());
    }

    /**
     * selection correct values of health field in SpaceMarine
     * @return correct value of health field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public Double fillHealth() throws ScriptException, InvalidInputException {
        return simpleSpaceMarineFiller.fill("Enter health", reader::readHealth);
    }

    /**
     * selection correct values of category field in SpaceMarine
     * @return correct value of category field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public AstartesCategory fillCategory() throws ScriptException, InvalidInputException {
        return simpleSpaceMarineFiller.fill("Chose the astartes category. Type SCOUT, INCEPTOR, TACTICAL or CHAPLAIN",
                reader::readCategory);
    }

    /**
     * selection correct values of weaponType field in SpaceMarine
     * @return correct value of weaponType field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public Weapon fillWeaponType() throws ScriptException, InvalidInputException {
        return simpleSpaceMarineFiller.fill("Chose the weapon type. Type HEAVY_BOLTGUN, BOLT_RIFLE, PLASMA_GUN"
                + " or INFERNO_PISTOL", reader::readWeaponType);
    }

    /**
     * selection correct values of meleeWeapon field in SpaceMarine
     * @return correct value of meleeWeapon field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public MeleeWeapon fillMeleeWeapon() throws ScriptException, InvalidInputException {
        return simpleSpaceMarineFiller.fill("Chose the melee weapon. Type CHAIN_AXE, MANREAPER, LIGHTING_CLAW,"
                        + " POWER_BLADE or POWER_FIST", reader::readMeleeWeapon);
    }

    /**
     * selection correct values of name field in Chapter
     * @return correct value of name field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public String fillChapterName() throws ScriptException, InvalidInputException {
        return simpleSpaceMarineFiller.fill("Enter chapter's name", reader::readNotEmptyString);
    }

    /**
     * selection correct values of world field in Chapter
     * @return correct value of world field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public String fillChapterWorld() throws ScriptException, InvalidInputException {
        return simpleSpaceMarineFiller.fill("Enter chapter's world", reader::readString);
    }

    /**
     * selection correct values of chapter field in SpaceMarine
     * @return correct value of chapter field
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public Chapter fillChapter() throws ScriptException, InvalidInputException {
        return new Chapter(this.fillChapterName(), this.fillChapterWorld());
    }

    /**
     * selection correct values of all fields in SpaceMarine
     * @return new object with entered values
     * @throws ScriptException if the value was incorrect and input manager read from a file
     */
    public SpaceMarine fillSpaceMarine() throws ScriptException, InvalidInputException {
        return new SpaceMarine(this.fillName(), this.fillCoordinates(), this.fillHealth(),
                this.fillCategory(), this.fillWeaponType(), this.fillMeleeWeapon(), this.fillChapter(), collectionManager);
    }
}
