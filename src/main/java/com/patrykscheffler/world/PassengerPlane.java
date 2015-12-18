package com.patrykscheffler.world;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Patryk on 05/12/2015.
 */
public final class PassengerPlane extends Plane {
    private PassengerContainer passengerContainer;
    private Building currentAirport;
    private Building nextAirport;

    public void setList(List<Path> list) {
        this.list = list;
    }

    private List<Path> list = new LinkedList<>();

    public final static Image image = new Image(PassengerPlane.class.getResourceAsStream("/img/plane.png"));

    public PassengerPlane() {
        super();
    }

    public PassengerPlane(int x, int y) {
        super(x, y);
        Runnable runner = this;
        Thread t = new Thread(runner);
        t.start();
    }

    @Override
    public Image getImage() {
        return image;
    }

    public Building getCurrentAirport() {
        return currentAirport;
    }

    public void setCurrentAirport(Airport currentAirport) {
        this.currentAirport = currentAirport;
    }

    public Building getNextAirport() {
        return nextAirport;
    }

    public void setNextAirport(Airport nextAirport) {
        this.nextAirport = nextAirport;
    }

    @Override
    public void move(Place A, Place B) {
        for (Path p : list) {
            this.setAngle(p.getAngle());
            move(p);
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
                Thread.sleep(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        currentAirport = World.BUILDINGS.get("Loleta");
        nextAirport = World.BUILDINGS.get("Kemah");
//        list.add(World.AIRPATHS.get("LoletaEarlton"));
//        list.add(World.AIRPATHS.get("EarltonKemah"));
//        list.add(World.AIRPATHS.get("KemahLoleta"));
//        list.add(World.AIRPATHS.get("LoletaKemah"));
//        list.add(World.AIRPATHS.get("KemahEarlton"));
//        list.add(World.AIRPATHS.get("EarltonLoleta"));
        while(true) {
            move(currentAirport, nextAirport);
        }

    }
}
