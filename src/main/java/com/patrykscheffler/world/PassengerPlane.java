package com.patrykscheffler.world;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class that represents Passenger plane.
 */
public final class PassengerPlane extends Plane implements Serializable {
    /** Passenger container */
    private PassengerContainer passengerContainer;

    /** Graphic which represents passenger plane */
    public final static Image image = new Image(PassengerPlane.class.getResourceAsStream("/img/plane1.png"));
    /** Graphic which represents selected passenger plane */
    public final static Image imageSelected = new Image(PassengerPlane.class.getResourceAsStream("/img/plane1-selected.png"));


    /** Default class constructor. It creates new passenger plane */
    public PassengerPlane() {
        super();
        List<PublicAirport> list = World.getPublicAirports();

        Random r = new Random();
        do {
            this.startBuilding = list.get(r.nextInt(list.size()));
            this.endBuilding = list.get(r.nextInt(list.size()));
        } while (startBuilding.getName().equals(endBuilding.getName()));

        Point position = startBuilding.getPosition();
        this.setXPosition(position.getXPosition());
        this.setYPosition(position.getYPosition());

        this.passengerContainer = new PassengerContainer(5);

        for (int i = 0; i < 5; i++) {
            Passenger passenger = new Passenger();
            passengerContainer.addPassenger(passenger);
        }

        Runnable runner = this;
        Thread t = new Thread(runner);
        t.start();
    }

    public PassengerContainer getPassengerContainer() {
        return passengerContainer;
    }

    public void setPassengerContainer(PassengerContainer passengerContainer) {
        this.passengerContainer = passengerContainer;
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
        return "Passenger Plane";
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
        list = findPath(startBuilding, endBuilding);

        while(this.isWorking) {
            move(startBuilding, endBuilding);
        }
    }



}
