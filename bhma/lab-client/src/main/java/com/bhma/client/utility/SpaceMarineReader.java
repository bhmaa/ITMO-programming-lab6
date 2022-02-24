package com.bhma.client.utility;

import com.bhma.client.data.*;
import com.bhma.client.exceptions.IllegalValueException;

import java.util.Locale;

public class SpaceMarineReader {
    private final InputManager inputManager;
    private final int minX = -685;

    public SpaceMarineReader(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public double readX() throws IllegalValueException, NumberFormatException {
        double x;
        String stringX;
        stringX = inputManager.read();
        x = Double.parseDouble(stringX);
        if (x <= minX) {
            throw new IllegalValueException("Value must be greater than " + minX);
        }
        return x;
    }

    public long readY() throws NumberFormatException {
        long y;
        String stringY;
        stringY = inputManager.read();
        y = Long.parseLong(stringY);
        return y;
    }

    public Double readHealth() throws IllegalValueException, NumberFormatException {
        Double health = null;
        String stringHealth;
        stringHealth = inputManager.read();
        if (!stringHealth.isEmpty()) {
            health = Double.parseDouble(stringHealth);
            if (health <= 0) {
                throw new IllegalValueException("Value must be greater than 0");
            }
        }
        return health;
    }

    public AstartesCategory readCategory() throws IllegalArgumentException {
        AstartesCategory category;
        category = AstartesCategory.valueOf(inputManager.read().toUpperCase(Locale.ROOT));
        return category;
    }

    public Weapon readWeaponType() throws IllegalArgumentException {
        Weapon weapon;
        weapon = Weapon.valueOf(inputManager.read().toUpperCase(Locale.ROOT));
        return weapon;
    }

    public MeleeWeapon readMeleeWeapon() throws IllegalArgumentException {
        MeleeWeapon meleeWeapon = null;
        String stringMeleeWeapon = inputManager.read();
        if (!stringMeleeWeapon.isEmpty()) {
            meleeWeapon = MeleeWeapon.valueOf(stringMeleeWeapon.toUpperCase(Locale.ROOT));
        }
        return meleeWeapon;
    }

    public String readNotNullString() throws IllegalValueException {
        String string;
        string = inputManager.read();
        if (string.isEmpty()) {
            throw new IllegalValueException("This field cannot be null");
        }
        return string;
    }
}
