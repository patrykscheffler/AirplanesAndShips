package com.patrykscheffler.world;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Class which represents concept of passenger container of vechicle
 */
public class PassengerContainer  implements Serializable {
    /** Max amout of passengers */
    private int maxCapacity;
    /** Collection which holds passengers */
    private LinkedList<Passenger> passengers;

    /** Constructor
     *
     * @param maxCapacity Max amount of passengers
     */
    public PassengerContainer(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        passengers = new LinkedList<>();
    }

    /** Adds passenger to collection
     *
     * @param passenger Passenger to add
     */
    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public LinkedList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(LinkedList<Passenger> passengers) {
        this.passengers = passengers;
    }
}
