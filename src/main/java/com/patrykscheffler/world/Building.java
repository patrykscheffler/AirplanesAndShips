package com.patrykscheffler.world;

import javafx.scene.image.Image;

/**
 * Created by Patryk on 05/12/2015.
 */
public abstract class Building extends Place {
    private int id;

    public Building() {
        super();
    }

    public Building(int x, int y) {
        super(x, y);
    }

    public Building(String name, int x, int y) {
        super(name, x, y);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract Image getImage();

    public void render() {

    }
}
