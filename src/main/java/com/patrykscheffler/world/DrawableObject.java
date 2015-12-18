package com.patrykscheffler.world;

/**
 * The basic abstract class of a com.patrykscheffler.world package.
 */
public abstract class DrawableObject {
    protected Point position;
    protected boolean shouldDraw = true;
    protected boolean isSelected = false;

    public DrawableObject() {
        position = new Point();
    }

    public DrawableObject(Point position) {
        this.position = position;
    }

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
}
