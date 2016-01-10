package com.patrykscheffler.world;

import java.io.Serializable;
import java.util.Random;

/**
 * Class which represents concept of plane
 */
public abstract class Plane extends Vechicle implements Serializable {
    /** Personel count */
    protected int personelCount;
    /** Fuel level */
    protected int fuelLevel;
    /** Plane name */
    protected String name;
    /** Field which hold information if emeregncy land */
    protected boolean emergencyLanding;

    /** Plane possible names */
    private final static String names[] = {"Agilecry", "Hellbomb", "Demoncore", "Nightroar", "Quickpunch", "Regal Bullet", "Sharp Voodoo", "Huge Vulture", "Small Spirit", "Vicious Bolt"};

    /** Default constructor */
    public Plane() {
        super();
        this.personelCount = 10;
        this.fuelLevel = 5000;
        this.emergencyLanding = false;

        Random r = new Random();
        this.name = names[r.nextInt(names.length)] + " " + (World.PLANES.size()+1);

        System.out.println("Created new Plane " + name);
    }

    /** Public constructor. It creates new plane in selected place
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Plane(int x, int y) {
        super(x, y);
        this.personelCount = 10;
        this.fuelLevel = 5000;
        this.emergencyLanding = false;

        Random r = new Random();
        this.name = names[r.nextInt(names.length)] + " " + (World.PLANES.size()+1);
    }

    public boolean isEmergencyLanding() {
        return emergencyLanding;
    }

    public void setEmergencyLanding(boolean emergencyLanding) {
        this.emergencyLanding = emergencyLanding;
    }

    public int getPersonelCount() {
        return personelCount;
    }

    public void setPersonelCount(int personelCount) {
        this.personelCount = personelCount;
    }

    public int getFuelLevel() {
        return fuelLevel;
    }

    /** Uses fuel */
    public void useFuel() {
        fuelLevel--;
    }

    /** Refills fuel */
    public void refillFuel() {
        fuelLevel = 5000;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
