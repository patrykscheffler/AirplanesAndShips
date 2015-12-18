package com.patrykscheffler.world;

import com.patrykscheffler.world.controllers.Home;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static javafx.application.Application.launch;

/**
 * World class of application
 */
public class World extends Application {
    private final static World WORLD = new World();
    private final static Random random = new Random();
    private final static Image image = new Image(World.class.getResourceAsStream("/img/map.png"));

    public final static HashMap<String, Vechicle> VECHICLES = new HashMap<>();
    public final static HashMap<String, Building> BUILDINGS = new HashMap<>();
    public final static HashMap<String, Path> AIRPATHS = new HashMap<>();
    public final static HashMap<String, Path> WATERPATHS = new HashMap<>();

    private Canvas map;

    public static World getInstance() {
        return WORLD;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        Parent root = loader.load();
        Home homeCtrl =  loader.getController();

        this.map = homeCtrl.getMap();
        final GraphicsContext context = this.map.getGraphicsContext2D();

        primaryStage.setTitle("World");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
        primaryStage.setScene(new Scene(root, 790, 590));
        primaryStage.setResizable(false);
        primaryStage.show();

        createBuildings();
        createRoads();
        createVechicles();

        this.map.setOnMousePressed((event) -> {
            int x = (int)event.getX();
            int y = (int)event.getY();

            System.out.println("x: " + x + ", y: " + y);
            for (Vechicle v : VECHICLES.values()) {
                v.setSelected(false);
                if (v.getXPosition() >= x - 20 && v.getXPosition() <= x + 20 && v.getYPosition() >= y - 20 && v.getYPosition() <= y + 20) {
                    System.out.println("Vechicle clicked");
                    v.setSelected(true);
                }
            }
        });

        Thread renderer = new Thread(){

            @Override
            public void run(){
                while (true) {
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException ex) {
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            context.clearRect(0, 0, context.getCanvas().getWidth(), context.getCanvas().getHeight());
                            context.drawImage(image, 0, 0);

                            draw(context);
                        }
                    });
                }
            }
        };

        renderer.setDaemon(true);
        renderer.start();
    }

    private void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    private void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
        gc.save(); // saves the current state on stack, including the current transform
        rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
        gc.drawImage(image, tlpx, tlpy);
        gc.restore(); // back to original state (before rotation)
    }

    public void draw(GraphicsContext context) {
        for (Building b : BUILDINGS.values()) {
            Image bImg = b.getImage();
            context.drawImage(bImg, b.getXPosition() - bImg.getWidth() / 2, b.getYPosition() - bImg.getHeight() / 2);
        }

        for (Vechicle v : VECHICLES.values()) {
            Image vImg = v.getImage();

            if (v.isSelected()) {
                context.setStroke(Color.LIGHTGREEN);
                context.setLineWidth(5);
                context.strokeOval(v.getXPosition() - vImg.getWidth(), v.getYPosition() - vImg.getHeight(), 50, 50);
            }

            drawRotatedImage(context, vImg, v.getAngle(), v.getXPosition() - vImg.getWidth() / 2, v.getYPosition() - vImg.getHeight() / 2);
        }

    }

    public void createBuildings() {
        Building loleta = new Airport("Loleta", 100, 400);
        Building kemah = new Airport("Kemah", 600, 50);
        Building earlton = new Airport("Earlton", 700, 450);

        BUILDINGS.put("Loleta", loleta);
        BUILDINGS.put("Kemah", kemah);
        BUILDINGS.put("Earlton", earlton);

    }

    public void createRoads() {
        addPathAndReversePath(BUILDINGS.get("Loleta"), BUILDINGS.get("Earlton"));
        addPathAndReversePath(BUILDINGS.get("Loleta"), BUILDINGS.get("Kemah"));
        addPathAndReversePath(BUILDINGS.get("Earlton"), BUILDINGS.get("Kemah"));
    }

    private void addPathAndReversePath(Place A, Place B) {
        Path p = new Path(A, B);
        Path rp = p.createReversePath();
        AIRPATHS.put(A.getName() + B.getName(), p);
        AIRPATHS.put(B.getName() + A.getName(), rp);
    }

    private void createVechicles() {
        Point position = BUILDINGS.get("Loleta").getPosition();
        Vechicle plane1 =  new PassengerPlane(position.getXPosition(), position.getYPosition());
        List<Path> list = new LinkedList<>();
        list.add(World.AIRPATHS.get("LoletaEarlton"));
        list.add(World.AIRPATHS.get("EarltonKemah"));
        list.add(World.AIRPATHS.get("KemahEarlton"));
        list.add(World.AIRPATHS.get("EarltonLoleta"));
        plane1.setList(list);
        VECHICLES.put("USS Enterprise", plane1);

        position = BUILDINGS.get("Kemah").getPosition();
        Vechicle plane2 =  new PassengerPlane(position.getXPosition(), position.getYPosition());
        list = new LinkedList<>();
        list.add(World.AIRPATHS.get("KemahLoleta"));
        list.add(World.AIRPATHS.get("LoletaKemah"));
        plane2.setList(list);
        VECHICLES.put("Another plane", plane2);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
