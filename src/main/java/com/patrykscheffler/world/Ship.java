package com.patrykscheffler.world;

import java.io.Serializable;
import java.util.Random;

/**
 * Class which represents concept of ship
 */
public abstract class Ship extends Vechicle implements Serializable {
    /* Ship name */
    protected String name;
    /** Ship possible names */
    private final static String names[] = {"The Hespeler", "Menace", "Curlew", "Gravelines", "The Persian", "The Griffin", "Poseidon", "Maori", "Crystal", "Ilfracombe"};

    /** Default constructor */
    public Ship() {
        super();

        Random r = new Random();
        this.name = names[r.nextInt(names.length)] + " " + (World.SHIPS.size()+1);
        //this.speed = r.nextInt(3) + 20;

        System.out.println("Created new Ship " + name);
    }

    /** Public constructor. It creates new ship in selected place
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Ship(int x, int y) {
        super(x, y);

        Random r = new Random();
        this.name = names[r.nextInt(names.length)] + " " + (World.SHIPS.size()+1);
        this.speed = r.nextInt(3) + 20;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
