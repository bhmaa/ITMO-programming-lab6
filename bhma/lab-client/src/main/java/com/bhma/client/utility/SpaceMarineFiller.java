package com.bhma.client.utility;

import com.bhma.client.data.*;
import com.bhma.client.exceptions.IllegalValueException;
import com.bhma.client.exceptions.ScriptException;

public class SpaceMarineFiller {
    private final SpaceMarineReader reader;
    private final InputManager inputManager;
    private final OutputManager outputManager;

    public SpaceMarineFiller(SpaceMarineReader reader, InputManager inputManager, OutputManager outputManager) {
        this.reader = reader;
        this.inputManager = inputManager;
        this.outputManager = outputManager;
    }

    public String fillName() throws ScriptException {
        String name;
        while (true) {
            try {
                outputManager.print("Enter name: ");
                name = reader.readNotNullString();
                break;
            } catch (IllegalValueException e) {
                outputManager.println(e.getMessage());
                if (inputManager.getScriptMode()) {
                    throw new ScriptException();
                }
            }
        }
        return name;
    }

    public double fillX() throws ScriptException {
        double x;
        while (true) {
            try {
                outputManager.print("Enter x coordinate: ");
                x = reader.readX();
                break;
            } catch (NumberFormatException ex) {
                outputManager.println("Value must be a number");
                if (inputManager.getScriptMode()) {
                    throw new ScriptException();
                }
            } catch (IllegalValueException e) {
                outputManager.println(e.getMessage());
                if (inputManager.getScriptMode()) {
                    throw new ScriptException();
                }
            }
        }
        return x;
    }

    public long fillY() throws ScriptException {
        long y;
        while (true) {
            try {
                outputManager.print("Enter y coordinate: ");
                y = reader.readY();
                break;
            } catch (NumberFormatException ex) {
                outputManager.println("Value must be a number");
                if (inputManager.getScriptMode()) {
                    throw new ScriptException();
                }
            }
        }
        return y;
    }

    public Coordinates fillCoordinates() throws ScriptException {
        return new Coordinates(this.fillX(), this.fillY());
    }

    public Double fillHealth() throws ScriptException {
        Double health;
        while (true) {
            try {
                outputManager.print("Enter health: ");
                health = reader.readHealth();
                break;
            } catch (NumberFormatException ex) {
                outputManager.println("Value must be a number");
                if (inputManager.getScriptMode()) {
                    throw new ScriptException();
                }
            } catch (IllegalValueException e) {
                outputManager.println(e.getMessage());
                if (inputManager.getScriptMode()) {
                    throw new ScriptException();
                }
            }
        }
        return health;
    }

    public AstartesCategory fillCategory() throws ScriptException {
        AstartesCategory category;
        while (true) {
            try {
                outputManager.print("Chose the astartes category. Type SCOUT, INCEPTOR, TACTICAL or CHAPLAIN: ");
                category = reader.readCategory();
                break;
            } catch (IllegalArgumentException e) {
                outputManager.println("Chose anything from list");
                if (inputManager.getScriptMode()) {
                    throw new ScriptException();
                }
            }
        }
        return category;
    }

    public Weapon fillWeaponType() throws ScriptException {
        Weapon weapon;
        while (true) {
            try {
                outputManager.print("Chose the weapon type. Type HEAVY_BOLTGUN, BOLT_RIFLE, PLASMA_GUN or INFERNO_PISTOL: ");
                weapon = reader.readWeaponType();
                break;
            } catch (IllegalArgumentException e) {
                outputManager.println("Chose anything from list");
                if (inputManager.getScriptMode()) {
                    throw new ScriptException();
                }
            }
        }
        return weapon;
    }

    public MeleeWeapon fillMeleeWeapon() throws ScriptException {
        MeleeWeapon meleeWeapon;
        while (true) {
            try {
                outputManager.print("Chose the melee weapon. Type CHAIN_AXE, MANREAPER, LIGHTING_CLAW, POWER_BLADE or POWER_FIST: ");
                meleeWeapon = reader.readMeleeWeapon();
                break;
            } catch (IllegalArgumentException e) {
                outputManager.println("Chose anything from list");
                if (inputManager.getScriptMode()) {
                    throw new ScriptException();
                }
            }
        }
        return meleeWeapon;
    }

    public String fillChapterName() throws ScriptException {
        String chapterName;
        while (true) {
            try {
                outputManager.print("Enter chapter's name: ");
                chapterName = reader.readNotNullString();
                break;
            } catch (IllegalValueException e) {
                outputManager.println(e.getMessage());
                if (inputManager.getScriptMode()) {
                    throw new ScriptException();
                }
            }
        }
        return chapterName;
    }

    public String fillChapterWorld() throws ScriptException {
        String chapterWorld;
        while (true) {
            try {
                outputManager.print("Enter chapter's world: ");
                chapterWorld = reader.readNotNullString();
                break;
            } catch (IllegalValueException e) {
                outputManager.println(e.getMessage());
                if (inputManager.getScriptMode()) {
                    throw new ScriptException();
                }
            }
        }
        return chapterWorld;
    }

    public Chapter fillChapter() throws ScriptException {
        return new Chapter(this.fillChapterName(), this.fillChapterWorld());
    }

    public SpaceMarine fillSpaceMarine() throws ScriptException {
        return new SpaceMarine(this.fillName(), this.fillCoordinates(), this.fillHealth(),
                this.fillCategory(), this.fillWeaponType(), this.fillMeleeWeapon(), this.fillChapter());
    }
}
