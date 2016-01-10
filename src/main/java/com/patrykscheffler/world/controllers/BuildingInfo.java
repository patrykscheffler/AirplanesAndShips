package com.patrykscheffler.world.controllers;

import com.patrykscheffler.world.Building;
import com.patrykscheffler.world.Vechicle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
public class BuildingInfo {
    @FXML
    private Label title;
    @FXML
    private Label name;
    @FXML
    private Label type;
    @FXML
    private ListView boardedVechicles;

    private Building building;

    public BuildingInfo() {

    }

    public void setBuilding(Building building) {
        this.building = building;
        prepareInfo();
    }

    private void prepareInfo() {
        if (building != null) {
            title.setText("BUILDING INFO");
            name.setText(building.getName());
            type.setText(building.getType());

            ArrayList<String> boarded = new ArrayList<>();
            HashMap<String, Vechicle> boarded2 = building.getLandedVechilces();
            if (boarded2.size() > 0) {
                for (Vechicle v : boarded2.values()) {
                    boarded.add(v.getName());
                }
                boardedVechicles.setItems(FXCollections.observableArrayList(boarded));
            }
        }
    }
}
