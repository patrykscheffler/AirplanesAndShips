package com.patrykscheffler.world;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Class which simulates concept of Airport
 */
public class MilitaryAirport extends Building  implements Serializable {
    /** Graphic which represents military plane */
    public final static Image image = new Image(MilitaryAirport.class.getResourceAsStream("/img/airport2.png"));

    /** Constructor which creates Military Airport in position and with name
     *
     * @param name Name of airport
     * @param x X coordinate
     * @param y Y coordinate
     */
    public MilitaryAirport(String name, int x, int y) {
        super(name, x, y);
    }

    public Image getImage() {
        return image;
    }

    @Override
    public String getType() {
        return "Military Airport";
    }
}