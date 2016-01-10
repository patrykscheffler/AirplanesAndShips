package com.patrykscheffler.world;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

/**
 * Class which represents concept of Passenger
 */
public class Passenger implements Serializable, Runnable {
    /** First name */
    private String firstName;
    /** Second name */
    private String secondName;
    /** Age */
    private int age;
    /** Pesel */
    private long pesel;
    /** Journey */
    private Journey journey;
    /** State of journey */
    private State state;
    /** Field which hold information if passegner is living */
    private boolean isLiving;
    /** Passenger possible names */
    private final static String firstNames[] = {"Raymond", "Lizzie", "Brittaney", "Bibi", "Xuan", "Karole"};
    /** Passenger possible names */
    private final static String secondNames[] = {"Bunner", "Garriga", "Benevides", "Otey", "Silver", "Mccane"};

    /** Default constructor */
    public Passenger() {
        Random r = new Random();
        this.firstName = firstNames[r.nextInt(firstNames.length)];
        this.secondName = secondNames[r.nextInt(secondNames.length)];
        this.age = r.nextInt(80) + 20;
        this.pesel = r.nextInt(99999)+100000;
        this.isLiving = true;
        this.state = State.TO;
        this.journey = new Journey();

        Runnable runner = this;
        Thread t = new Thread(runner);
        t.start();
    }

    @Override
    public void run() {

    }

    @Override
    public String toString() {
        return firstName + " " + secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getPesel() {
        return pesel;
    }

    public void setPesel(long pesel) {
        this.pesel = pesel;
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }
}
