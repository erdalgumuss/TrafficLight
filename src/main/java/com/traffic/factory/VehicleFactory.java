package com.traffic.factory;

import com.traffic.model.Direction;
import com.traffic.model.SpawnPoints;
import com.traffic.model.Vehicle;
import com.traffic.model.Vehicle.VehicleType;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class VehicleFactory {
    private static final Random random = new Random();

    public static Vehicle createVehicle(Direction direction) {
        VehicleType type = randomVehicleType();
        ImageView imageView = createVehicleImage(type);
        rotateImageByDirection(imageView, direction);  // yön bazlı döndür

        Group shape = new Group(imageView);

        // Başlangıç pozisyonu
        Point2D spawnPoint = SpawnPoints.get(direction);
        shape.setLayoutX(spawnPoint.getX());
        shape.setLayoutY(spawnPoint.getY());

        double speed = determineSpeed(type);
        return new Vehicle(direction, type, shape, speed);
    }

    private static VehicleType randomVehicleType() {
        VehicleType[] types = VehicleType.values();
        return types[random.nextInt(types.length)];
    }

    private static ImageView createVehicleImage(VehicleType type) {
        ImageView imageView = new ImageView();

        switch (type) {
            case CAR -> {
                String[] carImages = {"/assets/icons/vehicles/car1.png", "/assets/icons/vehicles/car2.png"};
                String chosen = carImages[random.nextInt(carImages.length)];
                imageView.setImage(new Image(chosen));
                imageView.setFitWidth(30);
                imageView.setFitHeight(50);
            }
            case TRUCK -> {
                imageView.setImage(new Image("/assets/icons/vehicles/truck.png"));
                imageView.setFitWidth(35);
                imageView.setFitHeight(65);
            }
            case VAN -> {
                imageView.setImage(new Image("/assets/icons/vehicles/van.png"));
                imageView.setFitWidth(32);
                imageView.setFitHeight(55);
            }
        }

        imageView.setPreserveRatio(true);
        return imageView;
    }

    private static void rotateImageByDirection(ImageView view, Direction direction) {
        switch (direction) {
            case NORTH -> view.setRotate(0);
            case SOUTH -> view.setRotate(180);
            case EAST -> view.setRotate(90);
            case WEST -> view.setRotate(270);
        }
    }

    private static double determineSpeed(VehicleType type) {
        return switch (type) {
            case CAR -> 2.0;
            case TRUCK -> 1.5;
            case VAN -> 1.8;
        };
    }
}
