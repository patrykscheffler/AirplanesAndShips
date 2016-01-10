package com.patrykscheffler.world;

import javafx.scene.image.Image;

import java.io.Serializable;

/**
 * Class which simulates concept of harbor.
 */
public class Harbor extends Building implements Serializable {
    /** Graphic which represents harbor */
    public final static Image image = new Image(Harbor.class.getResourceAsStream("/img/harbour.png"));

    public Harbor(String name, int x, int y) {
        super(name, x, y);
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public String getType() {
        return "Harbor";
    }
}
