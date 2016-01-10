package com.patrykscheffler.world;

import java.io.Serializable;

/**
 * Class that represents concept of place
 */
public class Place extends DrawableObject implements Serializable {
    /** Place name */
    protected String name = "";

    /** Default constructor */
    public Place() {super();}

    /** Constructor which create place in position
     *
     * @param position Positon object
     */
    public Place(Point position) {
         super(position);
    }

    /** Constructor which creates place in position
     *
     * @param x X position
     * @param y Y position
     */
    public Place(int x, int y) {
        super(x, y);
    }

    /** Constructor which creats place in position with name
     *
     * @param name Name of place
     * @param x X position
     * @param y Y position
     */
    public Place(String name, int x, int y) {
        super(x, y);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getType() {
        return "Place";
    }
}
