package com.patrykscheffler.world.controllers;

import com.patrykscheffler.world.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;

/**
 * Ship Info Controller
 */
public class ShipInfo {
    @FXML
    private Label title;
    @FXML
    private Label name;
    @FXML
    private ComboBox endHarbor;
    @FXML
    private ComboBox startHarbor;
    @FXML
    private Label speed;
    @FXML
    private Label type;
    @FXML
    private Label weapon;
    @FXML
    private Label company;
    @FXML
    private ListView passengers;

    /** Ship to show info about */
    private Ship ship;

    public void setShip(Ship ship) {
        this.ship = ship;
        prepareInfo();
    }

    /** Show info from ship object */
    private void prepareInfo() {
        if (ship != null) {
            ArrayList<String> harbors = new ArrayList<>();

            World.getHarbor().forEach(harbor ->
                    harbors.add(harbor.getName())
            );

            startHarbor.setItems(FXCollections.observableArrayList(harbors));
            endHarbor.setItems(FXCollections.observableArrayList(harbors));
            startHarbor.getSelectionModel().select(ship.getStartBuilding().getName());
            endHarbor.getSelectionModel().select(ship.getEndBuilding().getName());

            title.setText("SHIP INFO");
            name.setText(ship.getName());
            speed.setText(String.valueOf(ship.getSpeed()));
            type.setText(ship.getType());
            if (ship.getType().equalsIgnoreCase("Aircraft Carrier")) {
                AircraftCarrier ship = (AircraftCarrier) this.ship;
                weapon.setText(ship.getWeaponType().weaponType());
                passengers.setVisible(false);
                passengers.setMaxHeight(0);
            }
            if (ship.getType().equalsIgnoreCase("Cruise ship")) {
                CruiseShip ship = (CruiseShip) this.ship;
                company.setText(ship.getComapnyName());
                passengers.setItems(FXCollections.observableArrayList(ship.getPassengerContainer().getPassengers()));
            }
        }
    }

    /** Stop ship / remove */
    public void deleteShip(ActionEvent actionEvent) {
        if (ship != null) {
            ship.stopWorking();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
    }

    /** Change end building / destination */
    public void changeEndBuilding(ActionEvent actionEvent) {
        System.out.println("End building changed to " + endHarbor.getSelectionModel().getSelectedItem());
        ship.setEndBuilding(World.BUILDINGS.get(endHarbor.getSelectionModel().getSelectedItem()));
    }

    /** Change start building / starting point */
    public void changeStartBuilding(ActionEvent actionEvent) {
        System.out.println("Start building changed to " + startHarbor.getSelectionModel().getSelectedItem());
        ship.setStartBuilding(World.BUILDINGS.get(startHarbor.getSelectionModel().getSelectedItem()));
    }

    /** Show info about passenger */
    public void openPassengerInfo(Event event) throws Exception {
        int passengerId = passengers.getSelectionModel().getSelectedIndex();
        CruiseShip plane = (CruiseShip) this.ship;

        World.createPassengerInfo(plane.getPassengerContainer().getPassengers().get(passengerId));
    }
}
