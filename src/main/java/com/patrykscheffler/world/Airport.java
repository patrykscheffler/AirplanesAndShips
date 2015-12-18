package com.patrykscheffler.world;

import javafx.scene.image.Image;

import java.util.LinkedList;

/**
 * Class which simulates concept of Airport
 */
public class Airport extends Building {
    private int capacity;
    private LinkedList boardedPlanes;
    public final static Image image = new Image(PassengerPlane.class.getResourceAsStream("/img/airport.png"));

    public Airport() {
        super();
    }

    public Airport(int x, int y) {
        super(x, y);
    }

    public Airport(String name, int x, int y) {
        super(name, x, y);
    }

    public void spawnPassengerPlane() {

    }

    public Image getImage() {
        return image;
    }
}