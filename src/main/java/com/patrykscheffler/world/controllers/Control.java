package com.patrykscheffler.world.controllers;

import com.patrykscheffler.world.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Control {
    @FXML
    private Button toggleMusic;
    @FXML
    private Button togglePath;

    private MediaPlayer mediaPlayer;

    public Control() throws URISyntaxException {
        String path = "F:/MusicPlayer/src/musicplayer/adcBicycle_-_02_-_poor_economic_policies.mp3";
        Media media = new Media(Control.class.getResource("/mp3/sound.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    public void createPassengerPlane(ActionEvent actionEvent) {
        PassengerPlane plane = new PassengerPlane();
        World.PLANES.put(plane.getName(), plane);
    }

    public void createMilitaryPlane(ActionEvent actionEvent) {
        boolean bCreate = false;
        ArrayList<AircraftCarrier> list = (ArrayList<AircraftCarrier>) World.getAircraftCarriers();
        for (AircraftCarrier ac : list) {
            if (ac.isWorking()) {
                bCreate = true;
                break;
            }
        }

        if (bCreate) {
            MilitaryPlane plane = new MilitaryPlane();
            World.PLANES.put(plane.getName(), plane);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("There aren't any Aircraft Carriers in the World.");

            alert.showAndWait();
        }
    }

    public void createCruiseShip(ActionEvent actionEvent) {
        CruiseShip ship = new CruiseShip();
        World.SHIPS.put(ship.getName(), ship);
    }

    public void createAircraftCarrier(ActionEvent actionEvent) {
        AircraftCarrier ship = new AircraftCarrier();
        World.SHIPS.put(ship.getName(), ship);
    }

    public void toggleMusic(ActionEvent actionEvent) {
        MediaPlayer.Status status = mediaPlayer.getStatus();

        if (status == MediaPlayer.Status.PAUSED
                || status == MediaPlayer.Status.READY
                || status == MediaPlayer.Status.STOPPED) {

            mediaPlayer.play();
            toggleMusic.setText("Pause Music");

        } else {

            mediaPlayer.pause();
            toggleMusic.setText("Resume Music");

        }
    }

    public void exitApp(ActionEvent actionEvent) {
        for(Vechicle v : World.PLANES.values()) {
            v.stopWorking();
        }
        for(Vechicle v : World.SHIPS.values()) {
            v.stopWorking();
        }

        Platform.exit();
    }

    public void togglePath(ActionEvent actionEvent) {
        if (World.isShowPaths()) {
            World.setShowPaths(false);
            togglePath.setText("Show Paths");
        } else {
            World.setShowPaths(true);
            togglePath.setText("Hide Paths");
        }
    }

    public void saveWorld(ActionEvent actionEvent) {
        World.saveWorld();
    }

    public void loadWorld(ActionEvent actionEvent) {
        World.loadWorld();
    }
}
