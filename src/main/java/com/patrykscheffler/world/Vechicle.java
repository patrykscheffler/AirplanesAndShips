package com.patrykscheffler.world;


import javafx.scene.image.Image;

import java.util.List;

/**
 * Class that represents concept of vechicle.
 */
public abstract class Vechicle extends DrawableObject implements Moveable {
    private int id;

    private double angle = 0;
    private Path currentPath;

    protected Direction movementDirection = Direction.NONE;

    public Vechicle() {
        super();
    }

    public Vechicle(int x, int y) {
        super(x, y);
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

    public abstract Image getImage();

    public void startTravel() {

    }

    public void render() {

    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    public abstract void setList(List<Path> list);
}
