package com.patrykscheffler.world;

import java.io.Serializable;

/**
 * Class that represents concept of crossroad
 */
public class Crossroad extends Place implements Serializable {
    /** Field which holds information wheter crossroad is free */
    protected boolean isFree;
    /** Free Guard for synchronization */
    //protected final Object freeGuard = new Object();

    /** Constructor which creates Crossroad in position with name
     *
     * @param name Name of crossroad
     * @param x X position
     * @param y Y position
     */
    public Crossroad(String name, int x, int y) {
        super(name, x, y);
        this.isFree = true;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        synchronized (this) {
            isFree = free;
        }
    }
}
