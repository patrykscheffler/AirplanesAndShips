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
 * Control Panel Controller
 */
public class Control {
    @FXML
    private Button toggleMusic;
    @FXML
    private Button togglePath;

    /** Field used to play music */
    private MediaPlayer mediaPlayer;

    /** Constructor, loads music to mediaPlayer */
    public Control() throws URISyntaxException {
        String path = "F:/MusicPlayer/src/musicplayer/adcBicycle_-_02_-_poor_economic_policies.mp3";
        Media media = new Media(Control.class.getResource("/mp3/sound.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    /** Create passenger plane */
    public void createPassengerPlane(ActionEvent actionEvent) {
        PassengerPlane plane = new PassengerPlane();
        World.PLANES.put(plane.getName(), plane);
    }

    /** Create military plane */
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

    /** Create cruise ship */
    public void createCruiseShip(ActionEvent actionEvent) {
        CruiseShip ship = new CruiseShip();
        World.SHIPS.put(ship.getName(), ship);
    }

    /** Create aircraft carrier */
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

    /** Exit application, finishes every vechicle loop */
    public void exitApp(ActionEvent actionEvent) {
        for(Vechicle v : World.PLANES.values()) {
            v.stopWorking();
        }
        for(Vechicle v : World.SHIPS.values()) {
            v.stopWorking();
        }

        Platform.exit();
    }

    /** Show/hide path */
    public void togglePath(ActionEvent actionEvent) {
        if (World.isShowPaths()) {
            World.setShowPaths(false);
            togglePath.setText("Show Paths");
        } else {
            World.setShowPaths(true);
            togglePath.setText("Hide Paths");
        }
    }

    /** Save world / Serialization */
    public void saveWorld(ActionEvent actionEvent) {
        World.saveWorld();
    }

    /** Load world / deserialization */
    public void loadWorld(ActionEvent actionEvent) {
        World.loadWorld();
    }
}
