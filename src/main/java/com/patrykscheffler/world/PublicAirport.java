package com.patrykscheffler.world;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Class which simulates concept of Airport
 */
public class PublicAirport extends Building implements Serializable {
    /** Graphic which represents Public Airport */
    public final static Image image = new Image(PublicAirport.class.getResourceAsStream("/img/airport1.png"));

    /** Constructor which creates Public Airport in position with name
     *
     * @param name Name of an airport
     * @param x X coordinate
     * @param y Y coordinate
     */
    public PublicAirport(String name, int x, int y) {
        super(name, x, y);
    }

    public Image getImage() {
        return image;
    }

    @Override
    public String getType() {
        return "Public Airport";
    }
}