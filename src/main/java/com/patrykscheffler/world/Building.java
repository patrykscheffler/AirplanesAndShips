package com.patrykscheffler.world;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Class which simulates concept of Building.
 */
public abstract class Building extends Place implements Serializable {
    /** Amount of vechicles allowed to land */
    protected int capacity;
    //protected final Object boardedGuard = new Object();
    /** Collection which holds landed vechicles */
    protected HashMap<String, Vechicle> landedVechilces;

    /* Default constructor */
    public Building() {
        super();
    }

    /** Constructor which creates building in position
     *
     * @param x X coordinates
     * @param y Y coordinates
     */
    public Building(int x, int y) {
        super(x, y);
        landedVechilces = new HashMap<>();
        capacity = 5;
    }

    /** Constructor which creates building in position with name
     *
     * @param name Name of the building
     * @param x X coordinates
     * @param y Y coordinates
     */
    public Building(String name, int x, int y) {
        super(name, x, y);
        landedVechilces = new HashMap<>();
        capacity = 5;
    }

    public int getCapacity() {
        return capacity;
    }

    /** Returns collections with landed vechicles
     *
     * @return Collection with landed vechicles
     */
    public HashMap<String, Vechicle> getLandedVechilces() {
        return landedVechilces;
    }

    /** Chceck if vechicle can land
     *
     * @return true if vechicle can land, false otherwise
     */
    public boolean canBoard() {
        if (landedVechilces.size() < capacity) return true;
        return false;
    }

    /** Adds vechicle to landed vechicles collection
     *
     * @param v Vechicle to add
     */
    public void addVechicle(Vechicle v) {
        synchronized(this) {
            landedVechilces.put(v.getName(), v);
        }
    }

    /** Removes vechicle from landed vechicles collection
     *
     * @param v Vechicle to remove
     */
    public void removeVechicle(Vechicle v) {
        synchronized(this) {
            landedVechilces.remove(v.getName());
        }
    }

    public abstract Image getImage();
}
