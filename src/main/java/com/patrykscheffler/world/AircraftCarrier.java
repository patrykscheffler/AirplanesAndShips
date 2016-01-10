package com.patrykscheffler.world;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class that represents Aircraft carrier.
 */
public class AircraftCarrier extends Ship implements Serializable {
    private Weapon weaponType;
    /** Graphic which represents passenger plane */
    public final static Image image = new Image(AircraftCarrier.class.getResourceAsStream("/img/ship2.png"));
    /** Graphic which represents selected passenger plane */
    public final static Image imageSelected = new Image(AircraftCarrier.class.getResourceAsStream("/img/ship2-selected.png"));

    /** Default class constructor. It creates new aircraft carrier */
    public AircraftCarrier() {
        super();
        List<Harbor> list = World.getHarbor();

        Random r = new Random();
        do {
            this.startBuilding = list.get(r.nextInt(list.size()));
            this.endBuilding = list.get(r.nextInt(list.size()));
        } while (startBuilding.getName().equals(endBuilding.getName()));

        Point position = startBuilding.getPosition();
        this.setXPosition(position.getXPosition());
        this.setYPosition(position.getYPosition());
        this.weaponType = Weapon.values()[r.nextInt(Weapon.values().length)];
        Runnable runner = this;
        Thread t = new Thread(runner);
        t.start();
    }

    /** Public class construcor. It creates new aircraft carrier in selected place.
     *
     * @param x X cooridnates
     * @param y Y coordinates
     */
    public AircraftCarrier(int x, int y) {
        super(x, y);
        Runnable runner = this;
        Thread t = new Thread(runner);
        t.start();
    }

    public Weapon getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(Weapon weaponType) {
        this.weaponType = weaponType;
    }

    @Override
    public void move(Place A, Place B) {
        if (isWorking) {
            for (Path p : list) {
                this.setAngle(p.getAngle());
                move(p);
                if (!isWorking) return;
            }
        }
    }

    @Override
    public void move(Path p) {
        this.setMovementDirection(p.getDirection());
        int pX=0, pY=0;

        int eX = p.getBPoint().getXPosition();
        int eY  = p.getBPoint().getYPosition();

        for (Point point : p.path) {
            int x = point.getXPosition();
            int y = point.getYPosition();

            pX = x;
            pY = y;

            this.setPosition(x, y);

            try {
                Thread.sleep(this.speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        list = findPath(startBuilding, endBuilding);

        while(this.isWorking) {
            move(startBuilding, endBuilding);
        }
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public Image getImageSelected() {
        return imageSelected;
    }

    @Override
    public String getType() {
        return "Aircraft Carrier";
    }
}
