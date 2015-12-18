package com.patrykscheffler.world;

import javafx.scene.image.Image;

import java.util.List;

/**
 * Created by Patryk on 04/12/2015.
 */
public class AircraftCarrier extends Ship {
    private Weapon weaponType;

    public AircraftCarrier(Weapon weaponType) {
        this.weaponType = weaponType;
    }

    public void spawnMilitaryPlane() {

    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public void setList(List<Path> list) {

    }

    @Override
    public void move(Place A, Place B) {

    }

    @Override
    public void move(Path path) {

    }

    @Override
    public void run() {

    }
}
