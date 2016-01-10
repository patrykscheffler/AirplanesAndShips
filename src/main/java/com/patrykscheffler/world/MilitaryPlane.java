package com.patrykscheffler.world;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class that represents Military Plane.
 */
public class MilitaryPlane extends Plane implements Serializable {
    /** Weapon type */
    private Weapon weaponType;

    private Place startPosition;
    /** Graphic which represents military plane */
    public final static Image image = new Image(PassengerPlane.class.getResourceAsStream("/img/plane2.png"));
    /** Graphic which represents selected military plane */
    public final static Image imageSelected = new Image(PassengerPlane.class.getResourceAsStream("/img/plane2-selected.png"));

    /** Default class constructor. It creates new military plane */
    public MilitaryPlane() {
        super();
        List<MilitaryAirport> list = World.getMilitaryAirports();
        List<AircraftCarrier> ships = World.getAircraftCarriers();

        Random r = new Random();
        do {
            this.startBuilding = list.get(r.nextInt(list.size()));
            this.endBuilding = list.get(r.nextInt(list.size()));
        } while (startBuilding.getName().equals(endBuilding.getName()));

        AircraftCarrier startShip;
        do {
            startShip = ships.get(r.nextInt(ships.size()));
        } while (!startShip.isWorking());
        startPosition = new Place(startShip.getPosition());
        weaponType = startShip.getWeaponType();

        Point position = startBuilding.getPosition();
        this.setXPosition(position.getXPosition());
        this.setYPosition(position.getYPosition());

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
    public Image getImage() {
        return image;
    }

    @Override
    public Image getImageSelected() {
        return imageSelected;
    }

    @Override
    public void move(Place A, Place B) {
        if (isWorking) {
            for (Path p : list) {
                move(p);
                if (!isWorking) return;
            }
        }
    }

    @Override
    public void move(Path p) {
        setMovementDirection(p.getDirection());
        setAngle(p.getAngle());

        for (Point point : p.path) {
            if (point == p.path.get(0)) continue;

            int x = point.getXPosition();
            int y = point.getYPosition();
            Crossroad crossroad = null;

            for (Crossroad c : World.CROSSROADS.values()) {
                if (c.getPosition().getXPosition() == x && c.getPosition().getYPosition() == y) {
                    while(!c.isFree());

                    crossroad = c;
                    c.setFree(false);
                }
            }

            for (Building b : World.BUILDINGS.values()) {
                if (b.getPosition().getXPosition() == x && b.getPosition().getYPosition() == y) {
                    while(!b.canBoard());
                    b.addVechicle(this);
                    isVisible = false;
                    refillFuel();

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    while(emergencyLanding);

                    b.removeVechicle(this);
                    isVisible = true;
                }
            }

            setPosition(x, y);
            useFuel();

            if (crossroad != null) {
                crossroad.setFree(true);
                crossroad = null;
            }

            try {
                Thread.sleep(this.speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        Path firstPath = new Path(startPosition, startBuilding);
        move(firstPath);

        list = findPath(startBuilding, endBuilding);

        while(isWorking) {
            move(startBuilding, endBuilding);
        }
    }

    @Override
    public String getType() {
        return "Military Plane";
    }
}