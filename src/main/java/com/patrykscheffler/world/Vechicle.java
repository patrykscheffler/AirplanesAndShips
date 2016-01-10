package com.patrykscheffler.world;


import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Class that represents concept of vechicle.
 */
public abstract class Vechicle extends DrawableObject implements Moveable, Serializable {
    /** Angle of vechicle */
    protected double angle = 0;
    /** Current path */
    protected Path currentPath;
    /** Start position */
    protected Building startBuilding;
    /** End position */
    protected Building endBuilding;
    /** Movement direction */
    protected Direction movementDirection = Direction.NONE;
    /** Speed of vechicle */
    protected int speed;
    /** Field which holds information if Vechicle is working */
    protected boolean isWorking;
    /** Field which holds information if Vechicle is visible */
    protected boolean isVisible;
    /** List which contains paths */
    protected List<Path> list = new LinkedList<>();

    /** Default constructor */
    public Vechicle() {
        super();
        Random r = new Random();
        this.speed = r.nextInt(3) + 10;
        this.isWorking = true;
        this.isVisible = true;
    }

    /** Public constructor. It creates new vechicle in selected place
     *
     * @param x X coordinates
     * @param y Y coordinates
     */
    public Vechicle(int x, int y) {
        super(x, y);
        Random r = new Random();
        this.speed = r.nextInt(3) + 7;
        this.isWorking = true;
        this.isVisible = true;
    }

    public Path getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(Path currentPath) {
        this.currentPath = currentPath;
    }

    public Direction getMovementDirection() {
        return movementDirection;
    }

    public void setMovementDirection(Direction movementDirection) {
        this.movementDirection = movementDirection;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void stopWorking() {
        isWorking = false;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Building getStartBuilding() {
        return startBuilding;
    }

    /** Sets start building and finds new paths to follow
     *
     * @param startBuilding Start building
     */
    public void setStartBuilding(Building startBuilding) {
        this.startBuilding = startBuilding;
        list = findPath(startBuilding, endBuilding);
    }

    public Building getEndBuilding() {
        return endBuilding;
    }

    /** Sets end building and finds new paths to follow
     *
     * @param endBuilding End building
     */
    public void setEndBuilding(Building endBuilding) {
        this.endBuilding = endBuilding;
        list = findPath(startBuilding, endBuilding);
    }

    public void setList(List<Path> list) {
        this.list = list;
    }

    /** Finds paths from Place A to Place B
     *
     * @param A Start position
     * @param B End position
     * @return Paths list
     */
    public List<Path> findPath(Place A, Place B) {
        LinkedList<Path> list = new LinkedList<>();
        HashMap<String, Boolean> visited = new HashMap<>();

        for (String s : World.CROSSROADS.keySet()) {
            visited.put(s, false);
        }

        bruteForcePath(A, B, list, visited);

        return list;
    }

    /** Brute force algorithm to find paths
     *
     * @param current Current position
     * @param destination Destination position
     * @param path Current paths list
     * @param visited Croasroad visited list
     * @return
     */
    public boolean bruteForcePath(Place current, Place destination, LinkedList<Path> path, HashMap<String, Boolean> visited) {
        String expected = current.getName() + destination.getName();

        if (World.PATHS.containsKey(expected)) {
            path.addFirst(World.PATHS.get(expected));
            path.addLast(World.PATHS.get(destination.getName() + current.getName()));
            return true;
        }

        boolean bo = false;
        Path road1 = null;
        Path road2 = null;
        for (String s : World.CROSSROADS.keySet()) {
            if ((visited.get(s) == false) && (World.PATHS.containsKey(current.getName() + s))) {
                visited.put(s, true);

                boolean bobo = bruteForcePath(World.CROSSROADS.get(s), destination, path, visited);
                if (bobo == true) {
                    road1 = World.PATHS.get(current.getName() + s);
                    road2 = World.PATHS.get(s + current.getName());
                    bo = true;
                }

                visited.put(s, false);
            }
        }

        if (bo == true) {
            path.addFirst(road1);
            path.addLast(road2);
            return true;
        }

        return false;

    }

    /** Return name of vechicle
     *
     * @return Name of vechicle
     */
    public abstract String getName();

    /** Return image representing vechicle
     *
     * @return Image
     */
    public abstract Image getImage();

    /** Return image representsing selected vechicle
     *
     * @return Image
     */
    public abstract Image getImageSelected();
}
