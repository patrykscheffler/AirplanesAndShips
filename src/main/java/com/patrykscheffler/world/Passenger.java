package com.patrykscheffler.world;

/**
 * Created by Patryk on 05/12/2015.
 */
public class Passenger {
    private String firstName;
    private String secondName;
    private int age;
    private int PESEL;
    private Journey journey;
    private State state;

    public Passenger(String firstName, String secondName, int age, int PESEL, Journey journey) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.PESEL = PESEL;
        this.journey = journey;
    }

}
