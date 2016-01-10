package com.patrykscheffler.world;

/**
 * Weapon types
 */
public enum Weapon {
    MACHINE_GUNS ("Machine guns"),
    BOMBS ("Bombs"),
    LAZERS ("Lasers");

    private final String weaponType;

    Weapon(String weaponType) {
        this.weaponType = weaponType;
    }

    public String weaponType() { return weaponType;}
}
