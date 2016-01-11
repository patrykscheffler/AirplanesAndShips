package com.patrykscheffler.world.controllers;

import com.patrykscheffler.world.Passenger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Passenger Info Controller
 */
public class PassengerInfo {
    @FXML
    private Label firstName;
    @FXML
    private Label secondName;
    @FXML
    private Label age;
    @FXML
    private Label pesel;
    @FXML
    private Label home;
    @FXML
    private Label destination;
    @FXML
    private Label journeyType;

    /** Passenger to show info about */
    private Passenger passenger;

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
        prepareInfo();
    }

    /** Show info from passenger object */
    private void prepareInfo() {
        firstName.setText(passenger.getFirstName());
        secondName.setText(passenger.getSecondName());
        age.setText(String.valueOf(passenger.getAge()));
        pesel.setText(String.valueOf(passenger.getPesel()));
        home.setText(passenger.getJourney().getStartBuilding().getName());
        destination.setText(passenger.getJourney().getEndBuilding().getName());
        journeyType.setText(passenger.getJourney().getJourneyType().name());
    }
}
