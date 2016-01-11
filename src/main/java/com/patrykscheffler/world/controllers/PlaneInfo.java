package com.patrykscheffler.world.controllers;

import com.patrykscheffler.world.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;

/**
 * Plane Info Controller
 */
public class PlaneInfo {
    @FXML
    private Label title;
    @FXML
    private Label name;
    @FXML
    private ComboBox endAirport;
    @FXML
    private ComboBox startAirport;
    @FXML
    private Label speed;
    @FXML
    private Label type;
    @FXML
    private Label fuelLevel;
    @FXML
    private Label weapon;
    @FXML
    private Button landButton;
    @FXML
    private ListView passengers;

    /** Plane to show info about */
    private Plane plane;

    public void setPlane(Plane plane) {
        this.plane = plane;
        prepareInfo();
    }

    /** Show info from plane object */
    private void prepareInfo() {
        if (plane != null) {
            String planeType = plane.getType();
            ArrayList<String> airports = new ArrayList<>();

            if (planeType.equalsIgnoreCase("Passenger Plane")) {
                World.getPublicAirports().forEach(publicAirport ->
                        airports.add(publicAirport.getName())
                );
            } else if (planeType.equalsIgnoreCase("Military Plane")) {
                World.getMilitaryAirports().forEach(militaryAirport ->
                        airports.add(militaryAirport.getName())
                );
            }

            startAirport.setItems(FXCollections.observableArrayList(airports));
            endAirport.setItems(FXCollections.observableArrayList(airports));
            startAirport.getSelectionModel().select(plane.getStartBuilding().getName());
            endAirport.getSelectionModel().select(plane.getEndBuilding().getName());

            title.setText("PLANE INFO");
            name.setText(plane.getName());
            speed.setText(String.valueOf(plane.getSpeed()));
            fuelLevel.setText(String.valueOf(plane.getFuelLevel()));
            type.setText(plane.getType());

            if (plane.isEmergencyLanding()) {
                landButton.setText("Take off");
            } else {
                landButton.setText("Emergency land");
            }

            if (plane.getType().equalsIgnoreCase("Military Plane")) {
                MilitaryPlane plane = (MilitaryPlane) this.plane;
                weapon.setText(plane.getWeaponType().weaponType());
                passengers.setVisible(false);
                passengers.setMaxHeight(0);
            } else {
                PassengerPlane plane = (PassengerPlane) this.plane;
                passengers.setItems(FXCollections.observableArrayList(plane.getPassengerContainer().getPassengers()));
            }
        }
    }

    /** Stop plane / remove */
    public void deletePlane(ActionEvent actionEvent) {
        if (plane != null) {
            plane.stopWorking();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
    }

    /** Change end building / destination */
    public void changeEndBuilding(ActionEvent actionEvent) {
        System.out.println("End building changed to " + endAirport.getSelectionModel().getSelectedItem());
        plane.setEndBuilding(World.BUILDINGS.get(endAirport.getSelectionModel().getSelectedItem()));
    }

    /** Change start building / starting point */
    public void changeStartBuilding(ActionEvent actionEvent) {
        System.out.println("Start building changed to " + startAirport.getSelectionModel().getSelectedItem());
        plane.setStartBuilding(World.BUILDINGS.get(startAirport.getSelectionModel().getSelectedItem()));
    }

    /** Emergency landing */
    public void land(ActionEvent actionEvent) {
        if (plane.isEmergencyLanding()) {
            plane.setEmergencyLanding(false);
            landButton.setText("Emergency land");
        } else {
            plane.setEmergencyLanding(true);
            landButton.setText("Take off");
        }
    }

    /** Shows info about passenger */
    public void openPassengerInfo(Event event) throws Exception {
        int passengerId = passengers.getSelectionModel().getSelectedIndex();
        PassengerPlane plane = (PassengerPlane) this.plane;

        World.createPassengerInfo(plane.getPassengerContainer().getPassengers().get(passengerId));
    }
}
