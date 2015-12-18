package com.patrykscheffler.world;

/**
 * Created by Patryk on 06/12/2015.
 */
public class Place extends DrawableObject {
    protected String name = "";

    public Place() {
         super();
    }

    public Place(int x, int y) {
        super(x, y);
    }

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
}
