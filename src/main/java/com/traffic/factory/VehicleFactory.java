package com.traffic.factory;

import com.traffic.model.*;
import com.traffic.util.Constants;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class VehicleFactory {
    private static final Random random = new Random();

    public static Vehicle create(Direction direction, Lane lane) {
        // Turn belirle
        Vehicle.Turn turn = lane.name().startsWith("LEFT")
                ? (random.nextBoolean() ? Vehicle.Turn.LEFT : Vehicle.Turn.STRAIGHT)
                : (random.nextBoolean() ? Vehicle.Turn.RIGHT : Vehicle.Turn.STRAIGHT);

        // Görsel ve hız
        String path = Constants.VEHICLE_IMAGES[random.nextInt(Constants.VEHICLE_IMAGES.length)];
        Image image = new Image(VehicleFactory.class.getResourceAsStream(path));
        ImageView imageView = new ImageView(image);

        Vehicle.VehicleType type;
        double speed;

        if (path.contains("truck")) {
            type = Vehicle.VehicleType.TRUCK;
            imageView.setFitWidth(70);
            imageView.setFitHeight(45);
            speed = 1.5 + random.nextDouble();
        } else if (path.contains("van")) {
            type = Vehicle.VehicleType.VAN;
            imageView.setFitWidth(65);
            imageView.setFitHeight(40);
            speed = 1.8 + random.nextDouble();
        } else {
            type = Vehicle.VehicleType.CAR;
            imageView.setFitWidth(50);
            imageView.setFitHeight(30);
            speed = 2.5 + random.nextDouble() * 2.0;
        }

        imageView.setPreserveRatio(true);

        switch (direction) {
            case NORTH -> imageView.setRotate(0);
            case SOUTH -> imageView.setRotate(180);
            case EAST -> imageView.setRotate(90);
            case WEST -> imageView.setRotate(270);
        }

        Group shape = new Group(imageView);

        return new Vehicle(direction, lane, turn, type, shape, speed); // ✔ Doğru sıra
    }
}
