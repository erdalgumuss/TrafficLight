package com.traffic.view;

import com.traffic.model.Vehicle;
import com.traffic.util.Constants;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class VehicleView {
    private final Vehicle vehicle;
    private final Group shapeGroup;
    private final ImageView imageView;
    private final double halfWidth;
    private final double halfHeight;
    private double x;
    private double y;
    private List<Point2D> path;
    private int pathIndex = 0;

    public VehicleView(Vehicle vehicle, String imagePath) {
        this.vehicle = vehicle;
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        this.imageView = new ImageView(image);

        if (imagePath.contains("truck")) {
            imageView.setFitWidth(70);
            imageView.setFitHeight(45);
        } else if (imagePath.contains("van")) {
            imageView.setFitWidth(65);
            imageView.setFitHeight(40);
        } else {
            imageView.setFitWidth(50);
            imageView.setFitHeight(30);
        }

        imageView.setPreserveRatio(true);
        switch (vehicle.getDirection()) {
            case NORTH -> imageView.setRotate(0);
            case SOUTH -> imageView.setRotate(180);
            case EAST  -> imageView.setRotate(90);
            case WEST  -> imageView.setRotate(270);
        }

        this.halfWidth = imageView.getFitWidth() / 2.0;
        this.halfHeight = imageView.getFitHeight() / 2.0;
        this.shapeGroup = new Group(imageView);
        updatePosition();
    }

    public void move() {
        switch (vehicle.getDirection()) {
            case NORTH -> shapeGroup.setLayoutY(shapeGroup.getLayoutY() - vehicle.getSpeed());
            case SOUTH -> shapeGroup.setLayoutY(shapeGroup.getLayoutY() + vehicle.getSpeed());
            case EAST  -> shapeGroup.setLayoutX(shapeGroup.getLayoutX() + vehicle.getSpeed());
            case WEST  -> shapeGroup.setLayoutX(shapeGroup.getLayoutX() - vehicle.getSpeed());
        }
        updatePosition();
    }

    public void updatePosition() {
        Bounds b = shapeGroup.getBoundsInParent();
        this.x = b.getMinX() + halfWidth;
        this.y = b.getMinY() + halfHeight;
    }

    public boolean hasReachedStopLine() {
        Bounds b = shapeGroup.getBoundsInParent();
        return switch (vehicle.getDirection()) {
            case NORTH -> b.getMinY() <= Constants.STOP_LINE_NORTH_Y;
            case SOUTH -> b.getMaxY() >= Constants.STOP_LINE_SOUTH_Y;
            case EAST  -> b.getMaxX() >= Constants.STOP_LINE_EAST_X;
            case WEST  -> b.getMinX() <= Constants.STOP_LINE_WEST_X;
        };
    }

    public boolean hasPassedIntersection() {
        return isOutOfBounds(Constants.SIMULATION_WIDTH, Constants.SIMULATION_HEIGHT);
    }

    public boolean isOutOfBounds(double sceneWidth, double sceneHeight) {
        Bounds b = shapeGroup.getBoundsInParent();
        return (b.getMaxX() < -50 || b.getMinX() > sceneWidth + 50
                || b.getMaxY() < -50 || b.getMinY() > sceneHeight + 50);
    }

    public boolean checkCollisionWith(VehicleView other) {
        Bounds a = shapeGroup.getBoundsInParent();
        Bounds o = other.shapeGroup.getBoundsInParent();
        return a.intersects(o);
    }

    public double distanceTo(VehicleView other) {
        return Math.hypot(this.x - other.x, this.y - other.y);
    }

    public void setPath(List<Point2D> path) {
        this.path = path;
        this.pathIndex = 0;
    }

    public void followPath() {
        if (path == null || pathIndex >= path.size()) return;
        Point2D target = path.get(pathIndex);
        shapeGroup.setLayoutX(target.getX());
        shapeGroup.setLayoutY(target.getY());
        pathIndex++;
        updatePosition();
    }

    public Group getShape() {
        return shapeGroup;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
