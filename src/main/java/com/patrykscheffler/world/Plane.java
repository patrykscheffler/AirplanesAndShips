package com.patrykscheffler.world;

import javafx.scene.image.Image;

/**
 * Created by Patryk on 05/12/2015.
 */
public abstract class Plane extends Vechicle {
    private int personelCount;
    private float fuelLevel;

    public Plane() {
        super();
        this.personelCount = 10;
        this.fuelLevel = 100;
    }

    public Plane(int x, int y) {
        super(x, y);
        this.personelCount = 10;
        this.fuelLevel = 100;
    }

    public int getPersonelCount() {
        return personelCount;
    }

    public void setPersonelCount(int personelCount) {
        this.personelCount = personelCount;
    }

    public float getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(float fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public void useFuel(float fuel) {

    }

    public void refillFuel() {

    }

    public void land() {

    }

    public void takeOff() {

    }
}
