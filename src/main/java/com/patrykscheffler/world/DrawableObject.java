package com.patrykscheffler.world;

import java.io.Serializable;

/**
 * The basic abstract class of a com.patrykscheffler.world package.
 */
public abstract class DrawableObject implements Serializable {
    /** Position of an object */
    protected Point position;
    /** Field which holds information if object should be drawn */
    protected boolean shouldDraw = true;
    /** Field which holds information if object is selected */
    protected boolean isSelected = false;

    /** Default constructor */
    public DrawableObject() {
        position = new Point();
    }

    /** Constructor which creates drawable object in position */
    public DrawableObject(Point position) {
        this.position = position;
    }

    /** Constructor which creates drawable object in position
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    public DrawableObject(int x, int y) {
        position = new Point(x, y);
    }

    public boolean isShouldDraw() {
        return shouldDraw;
    }

    public void setShouldDraw(boolean shouldDraw) {
        this.shouldDraw = shouldDraw;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Point getPosition() {
        return new Point(position);
    }

    public int getXPosition() {
        return position.getXPosition();
    }

    public void setXPosition(int xPosition) {
        position.setXPosition(xPosition);
    }

    public int getYPosition() {
        return position.getYPosition();
    }

    public void setYPosition(int yPosition) {
        position.setXPosition(yPosition);
    }

    public void setPosition(int x, int y) {
        position.setXPosition(x);
        position.setYPosition(y);
    }

    /** Returns type name
     *
     * @return Type name
     */
    public abstract String getType();
}
