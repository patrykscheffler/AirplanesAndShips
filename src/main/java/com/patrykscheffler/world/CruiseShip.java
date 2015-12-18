package com.patrykscheffler.world;

import javafx.scene.image.Image;

import java.util.List;

/**
 * Created by Patryk on 05/12/2015.
 */
public class CruiseShip extends Ship {
    private PassengerContainer passengerContainer;
    private Harbor currentHarbor;
    private Harbor nextHarbor;

    public void CruiseShip() {

    }

    public Harbor getCurrentHarbor() {
        return currentHarbor;
    }

    public void setCurrentHarbor(Harbor currentHarbor) {
        this.currentHarbor = currentHarbor;
    }

    public Harbor getNextHarbor() {
        return nextHarbor;
    }

    public void setNextHarbor(Harbor nextHarbor) {
        this.nextHarbor = nextHarbor;
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
