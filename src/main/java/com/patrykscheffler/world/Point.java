package com.patrykscheffler.world;

/**
 * Class that represents concept of point, the smallest single element on map.
 */
public class Point {
    private int xPosition;
    private int yPosition;

    public Point() {}

    public Point(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public Point(Point p) {
        this.xPosition = p.getXPosition();
        this.yPosition = p.getYPosition();
    }

    public int getXPosition() {
        return xPosition;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }
}