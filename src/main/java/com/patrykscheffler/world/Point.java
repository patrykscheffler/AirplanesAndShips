package com.patrykscheffler.world;

import java.io.Serializable;

/**
 * Class that represents concept of point, the smallest single element on map.
 */
public class Point implements Serializable {
    /** X coordinate */
    private int xPosition;
    /** Y coordinate */
    private int yPosition;

    /** Default construcotr which creates Point */
    public Point() {}

    /** Constructor
     *
     * @param xPosition X coordinate
     * @param yPosition Y coordinate
     */
    public Point(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    /** Constructor
     *
     * @param p Point
     */
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