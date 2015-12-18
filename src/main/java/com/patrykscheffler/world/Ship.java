package com.patrykscheffler.world;

import javafx.scene.image.Image;

/**
 * Created by Patryk on 05/12/2015.
 */
public abstract class Ship extends Vechicle {
    private int maxSpeed;

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
