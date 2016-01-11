package com.patrykscheffler.world;

import com.patrykscheffler.world.controllers.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

import static javafx.application.Application.launch;

/**
 * World class of application
 */
public class World extends Application implements Serializable {

    /** Enables randomization */
    private final static Random random = new Random();
    /** Graphic of World */
    private final static Image image = new Image(World.class.getResourceAsStream("/img/map.png"));
    /** Field which tells if path should be shown */
    private static boolean showPaths = false;

    /** List of all planes */
    public static HashMap<String, Vechicle> PLANES = new HashMap<>();
    /** List of all ships */
    public static HashMap<String, Vechicle> SHIPS = new HashMap<>();
    /** List of all buidlings */
    public static HashMap<String, Building> BUILDINGS = new HashMap<>();
    /** List of all crossroads */
    public static HashMap<String, Crossroad> CROSSROADS = new HashMap<>();
    /** List of all paths */
    public static HashMap<String, Path> PATHS = new HashMap<>();

    /** Canvas used to draw objects */
    private Canvas map;

    /** Main method for application */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        Parent root = loader.load();
        Home homeCtrl =  loader.getController();

        this.map = homeCtrl.getMap();
        final GraphicsContext context = this.map.getGraphicsContext2D();

        primaryStage.setTitle("World");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
        primaryStage.setScene(new Scene(root, 1280, 750));
        //primaryStage.setResizable(false);
        primaryStage.show();

        createControlPanel();
        createBuildings();
        createCrossroads();
        createRoads();
        //createVechicles();

        this.map.setOnMousePressed((event) -> {
            int x = (int)event.getX();
            int y = (int)event.getY();

            System.out.println("x: " + x + ", y: " + y);

            for (Building b : BUILDINGS.values()) {
                b.setSelected(false);
                if (b.getXPosition() >= x - 20 && b.getXPosition() <= x + 20 && b.getYPosition() >= y - 20 && b.getYPosition() <= y + 20) {
                    String name = b.getName();
                    System.out.println(name + " clicked");
                    b.setSelected(true);
                    try {
                        createBuildingInfo(b);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            for (Vechicle v : PLANES.values()) {
                v.setSelected(false);
                if (v.getXPosition() >= x - 20 && v.getXPosition() <= x + 20 && v.getYPosition() >= y - 20 && v.getYPosition() <= y + 20) {
                    String name = v.getName();
                    System.out.println(name + " clicked");
                    v.setSelected(true);
                    try {
                        createPlaneInfo((Plane) v);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            for (Vechicle v : SHIPS.values()) {
                v.setSelected(false);
                if (v.getXPosition() >= x - 20 && v.getXPosition() <= x + 20 && v.getYPosition() >= y - 20 && v.getYPosition() <= y + 20) {
                    String name = v.getName();
                    System.out.println(name + " clicked");
                    v.setSelected(true);
                    try {
                        createShipInfo((Ship) v);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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

    /** Rotates image */
    private void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    /** Draws Rotated Image */
    private void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
        gc.save(); // saves the current state on stack, including the current transform
        rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
        gc.drawImage(image, tlpx + image.getWidth() / 2, tlpy);
        gc.restore(); // back to original state (before rotation)
    }

    /** Draws every element */
    public void draw(GraphicsContext context) {
        for (Building b : BUILDINGS.values()) {
            Image bImg = b.getImage();
            context.drawImage(bImg, b.getXPosition() - bImg.getWidth() / 2, b.getYPosition() - bImg.getHeight() / 2);
        }

        for (Vechicle v : SHIPS.values()) {
            Image vImg = v.getImage();
            if (v.isSelected()) {
                vImg = v.getImageSelected();
            }

            if (!v.isWorking()) {
                continue;
            }

            if (v.isVisible()) {
                drawRotatedImage(context, vImg, v.getAngle(), v.getXPosition() - vImg.getWidth() / 2, v.getYPosition() - vImg.getHeight() / 2);
            }
        }

        for (Vechicle v : PLANES.values()) {
            Image vImg = v.getImage();
            if (v.isSelected()) {
                vImg = v.getImageSelected();
            }

            if (!v.isWorking()) {
                continue;
            }

//            if (v.isSelected()) {
//                context.setStroke(Color.LIGHTGREEN);
//                context.setLineWidth(5);
//                context.strokeOval(v.getXPosition() - vImg.getWidth(), v.getYPosition() - vImg.getHeight(), 50, 50);
//            }
            if (v.isVisible()) {
                drawRotatedImage(context, vImg, v.getAngle(), v.getXPosition() - vImg.getWidth() / 2, v.getYPosition() - vImg.getHeight() / 2);
            }
        }

        if (showPaths) {
            for (Path ap : PATHS.values()) {
                Point A = ap.getAPoint();
                Point B = ap.getBPoint();

                context.setStroke(Color.web("#FFFFFF", 0.1));
                context.setLineWidth(1);
                context.strokeLine(A.getXPosition(), A.getYPosition(), B.getXPosition(), B.getYPosition());
            }
        }
    }

    /** Creates buildings */
    public void createBuildings() {
        BUILDINGS.put("Loleta", new PublicAirport("Loleta", 186, 40));
        BUILDINGS.put("Kemah", new PublicAirport("Kemah", 181, 457));
        BUILDINGS.put("Earlton", new PublicAirport("Earlton", 70, 674));
        BUILDINGS.put("Stanford", new PublicAirport("Stanford", 783, 93));
        BUILDINGS.put("Reardan", new PublicAirport("Reardan", 710, 478));
        BUILDINGS.put("Warsaw", new PublicAirport("Warsaw", 1138, 520));

        BUILDINGS.put("Vernonia", new MilitaryAirport("Vernonia", 1176, 672));
        BUILDINGS.put("Andover", new MilitaryAirport("Andover", 52, 239));
        BUILDINGS.put("Marshallton", new MilitaryAirport("Marshallton", 1133, 64));
        BUILDINGS.put("Winger", new MilitaryAirport("Winger", 512, 643));

        BUILDINGS.put("Camarillo", new Harbor("Camarillo", 977, 123));
        BUILDINGS.put("Millerton", new Harbor("Millerton", 956, 539));
        BUILDINGS.put("Sigel", new Harbor("Sigel", 226, 229));
        BUILDINGS.put("Gilboa", new Harbor("Gilboa", 575, 62));
        BUILDINGS.put("Dorset", new Harbor("Dorset", 527, 399));
    }

    /** Creates crossroads */
    public void createCrossroads() {
        CROSSROADS.put("Crossroad1", new Crossroad("Crossroad1", 420, 290));
        CROSSROADS.put("Crossroad2", new Crossroad("Crossroad2", 387, 591));
        CROSSROADS.put("Crossroad3", new Crossroad("Crossroad3", 846, 246));

        CROSSROADS.put("Crossroad4", new Crossroad("Crossroad4", 888, 652));
        CROSSROADS.put("Crossroad5", new Crossroad("Crossroad5", 1134, 242));

        CROSSROADS.put("Crossroad6", new Crossroad("Crossroad6", 968, 333));
        CROSSROADS.put("Crossroad7", new Crossroad("Crossroad7", 487, 204));
    }

    /** Creates roads */
    public void createRoads() {
        addPathAndReversePath(CROSSROADS.get("Crossroad1"), BUILDINGS.get("Kemah"));
        addPathAndReversePath(CROSSROADS.get("Crossroad1"), BUILDINGS.get("Loleta"));
        addPathAndReversePath(CROSSROADS.get("Crossroad1"), CROSSROADS.get("Crossroad2"));
        addPathAndReversePath(CROSSROADS.get("Crossroad1"), CROSSROADS.get("Crossroad3"));
        addPathAndReversePath(CROSSROADS.get("Crossroad2"), BUILDINGS.get("Earlton"));
        addPathAndReversePath(CROSSROADS.get("Crossroad2"), BUILDINGS.get("Reardan"));
        addPathAndReversePath(CROSSROADS.get("Crossroad3"), BUILDINGS.get("Stanford"));
        addPathAndReversePath(CROSSROADS.get("Crossroad5"), BUILDINGS.get("Warsaw"));

        addPathAndReversePath(CROSSROADS.get("Crossroad4"), BUILDINGS.get("Winger"));
        addPathAndReversePath(CROSSROADS.get("Crossroad4"), BUILDINGS.get("Vernonia"));
        addPathAndReversePath(CROSSROADS.get("Crossroad1"), BUILDINGS.get("Andover"));
        addPathAndReversePath(CROSSROADS.get("Crossroad5"), BUILDINGS.get("Marshallton"));

        addPathAndReversePath(CROSSROADS.get("Crossroad3"), CROSSROADS.get("Crossroad4"));
        addPathAndReversePath(CROSSROADS.get("Crossroad3"), CROSSROADS.get("Crossroad5"));

        addPathAndReversePath(CROSSROADS.get("Crossroad6"), CROSSROADS.get("Crossroad7"));

        addPathAndReversePath(CROSSROADS.get("Crossroad6"), BUILDINGS.get("Camarillo"));
        addPathAndReversePath(CROSSROADS.get("Crossroad6"), BUILDINGS.get("Millerton"));
        addPathAndReversePath(CROSSROADS.get("Crossroad7"), BUILDINGS.get("Sigel"));
        addPathAndReversePath(CROSSROADS.get("Crossroad7"), BUILDINGS.get("Gilboa"));
        addPathAndReversePath(CROSSROADS.get("Crossroad7"), BUILDINGS.get("Dorset"));
    }

    /** Creates two paths with the same run but reverse direction
     *
     * @param A Start place
     * @param B End place
     */
    private void addPathAndReversePath(Place A, Place B) {
        Path p = new Path(A, B);
        Path rp = p.createReversePath();
        PATHS.put(A.getName() + B.getName(), p);
        PATHS.put(B.getName() + A.getName(), rp);
    }

    /** Creates vechicles */
    private void createVechicles() {

        PassengerPlane plane1 =  new PassengerPlane();
        PLANES.put(plane1.getName(), plane1);

        PassengerPlane plane2 = new PassengerPlane();
        PLANES.put(plane2.getName(), plane2);
    }

    /** Creates plane info window
     *
     * @param plane Plane object to display
     */
    private void createPlaneInfo(Plane plane) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/plane.fxml"));
        Parent root = loader.load();

        PlaneInfo controller = loader.getController();
        controller.setPlane(plane);

        Scene secondScene = new Scene(root);
        Stage secondStage = new Stage();
        secondStage.setTitle("Plane info");
        secondStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
        secondStage.setScene(secondScene);
        secondStage.setResizable(false);
        secondStage.show();
    }

    /** Creates control panel window */
    private void createControlPanel() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/control.fxml"));
        Scene secondScene = new Scene(loader.load());
        Stage secondStage = new Stage();
        secondStage.setTitle("World - Control Panel");
        secondStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
        secondStage.setScene(secondScene);
        secondStage.setResizable(false);
        secondStage.show();
    }

    /** Creates ship info window
     *
     * @param ship Ship object to display
     */
    private void createShipInfo(Ship ship) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ship.fxml"));
        Parent root = loader.load();

        ShipInfo controller = loader.getController();
        controller.setShip(ship);

        Scene secondScene = new Scene(root);
        Stage secondStage = new Stage();
        secondStage.setTitle("Ship info");
        secondStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
        secondStage.setScene(secondScene);
        secondStage.setResizable(false);
        secondStage.show();
    }

    /** Create building info windows
     *
     * @param building Building object to show
     */
    private void createBuildingInfo(Building building) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/building.fxml"));
        Parent root = loader.load();

        BuildingInfo controller = loader.getController();
        controller.setBuilding(building);

        Scene secondScene = new Scene(root);
        Stage secondStage = new Stage();
        secondStage.setTitle("Building info");
        secondStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
        secondStage.setScene(secondScene);
        secondStage.setResizable(false);
        secondStage.show();
    }

    /** Create passenger info windows
     *
     * @param passenger Passenger object to show
     */
    public static void createPassengerInfo(Passenger passenger) throws Exception {
        FXMLLoader loader = new FXMLLoader(World.class.getResource("/fxml/passenger.fxml"));
        Parent root = loader.load();

        PassengerInfo controller = loader.getController();
        controller.setPassenger(passenger);

        Scene secondScene = new Scene(root);
        Stage secondStage = new Stage();
        secondStage.setTitle("Passenger info");
        secondStage.getIcons().add(new Image(World.class.getResourceAsStream("/img/icon.png")));
        secondStage.setScene(secondScene);
        secondStage.setResizable(false);
        secondStage.show();
    }

    public static List<PublicAirport> getPublicAirports() {
        List<PublicAirport> list = new ArrayList<>();
        for (Building b : BUILDINGS.values()) {
            if (b.getType().equalsIgnoreCase("Public Airport")) {
                list.add((PublicAirport) b);
            }
        }
        return list;
    }

    public static List<MilitaryAirport> getMilitaryAirports() {
        List<MilitaryAirport> list = new ArrayList<>();
        for (Building b : BUILDINGS.values()) {
            if (b.getType().equalsIgnoreCase("Military Airport")) {
                list.add((MilitaryAirport) b);
            }
        }
        return list;
    }

    public static List<Harbor> getHarbor() {
        List<Harbor> list = new ArrayList<>();
        for (Building b : BUILDINGS.values()) {
            if (b.getType().equalsIgnoreCase("Harbor")) {
                list.add((Harbor) b);
            }
        }
        return list;
    }

    public static List<PassengerPlane> getPassengerPlanes() {
        List<PassengerPlane> list = new ArrayList<>();
        for (Vechicle v : PLANES.values()) {
            if (v.getType().equalsIgnoreCase("Passegner Plane")) {
                list.add((PassengerPlane) v);
            }
        }
        return list;
    }

    public static List<MilitaryPlane> getMilitaryPlanes() {
        List<MilitaryPlane> list = new ArrayList<>();
        for (Vechicle v : PLANES.values()) {
            if (v.getType().equalsIgnoreCase("Military Plane")) {
                list.add((MilitaryPlane) v);
            }
        }
        return list;
    }

    public static List<CruiseShip> getCruiseShips() {
        List<CruiseShip> list = new ArrayList<>();
        for (Vechicle v : SHIPS.values()) {
            if (v.getType().equalsIgnoreCase("Cruise Ship")) {
                list.add((CruiseShip) v);
            }
        }
        return list;
    }

    public static List<AircraftCarrier> getAircraftCarriers() {
        List<AircraftCarrier> list = new ArrayList<>();
        for (Vechicle v : SHIPS.values()) {
            if (v.getType().equalsIgnoreCase("Aircraft Carrier")) {
                list.add((AircraftCarrier) v);
            }
        }
        return list;
    }

    /** Serializes game state binary */
    public static void saveWorld() {
        String nazwaPliku = "save";
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(nazwaPliku)));


            oos.writeObject(PLANES);
            oos.writeObject(SHIPS);
            oos.writeObject(BUILDINGS);
            oos.writeObject(CROSSROADS);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Deserializes game state from binary file.*/
    public static void loadWorld() {
        for (Vechicle v : PLANES.values()) {
            v.stopWorking();
        }
        for (Vechicle v : SHIPS.values()) {
            v.stopWorking();
        }

        String nazwaPliku = "save";
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(nazwaPliku)));

            PLANES = (HashMap<String, Vechicle>) ois.readObject();
            SHIPS = (HashMap<String, Vechicle>) ois.readObject();
            BUILDINGS = (HashMap<String, Building>) ois.readObject();
            CROSSROADS = (HashMap<String, Crossroad>) ois.readObject();

            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (Vechicle v : PLANES.values()) {
            Thread t = new Thread(v);
            t.start();
        }

        for (Vechicle v : SHIPS.values()) {
            Thread t = new Thread(v);
            t.start();
        }
    }

    public static boolean isShowPaths() {
        return showPaths;
    }

    public static void setShowPaths(boolean showPaths) {
        World.showPaths = showPaths;
    }

    /** Main method */
    public static void main(String[] args) {
        launch(args);
    }
}
