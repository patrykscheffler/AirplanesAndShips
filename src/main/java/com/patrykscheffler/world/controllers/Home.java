package com.patrykscheffler.world.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

/**
 * Home controller
 */
public class Home {
    @FXML
    private Canvas map;

    public Canvas getMap() {
        return this.map;
    }
}
