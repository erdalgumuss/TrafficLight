package com.traffic.model;
import com.traffic.util.Constants;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;

import java.util.List;

public class Vehicle {
    public enum VehicleType { CAR, TRUCK, VAN }
    public enum Turn { STRAIGHT, LEFT, RIGHT }

    private final Direction direction;
    private final Lane lane;
    private final Turn turn;
    private final VehicleType type;
    private final Group shape; // Görsel nesne (imageView dahil)
    private final double speed; // Hareket hızı (px/frame)

    private long enqueueTimeMillis;
    private List<Point2D> path; // dönüş yolu için
    private int pathIndex = 0;

    private double x;
    private double y;

    public Vehicle(Direction direction, Lane lane, Turn turn, VehicleType type, Group shape, double speed) {
        this.direction = direction;
        this.lane = lane;
        this.turn = turn;
        this.type = type;
        this.shape = shape;
        this.speed = speed;
        updatePosition();
    }

    public void move() {
        switch (direction) {
            case NORTH -> shape.setLayoutY(shape.getLayoutY() - speed);
            case SOUTH -> shape.setLayoutY(shape.getLayoutY() + speed);
            case EAST  -> shape.setLayoutX(shape.getLayoutX() + speed);
            case WEST  -> shape.setLayoutX(shape.getLayoutX() - speed);
        }
        updatePosition();
    }

    public void updatePosition() {
        Bounds b = shape.getBoundsInParent();
        this.x = b.getMinX() + shape.getBoundsInParent().getWidth() / 2.0;
        this.y = b.getMinY() + shape.getBoundsInParent().getHeight() / 2.0;
    }

    public void setPath(List<Point2D> path) {
        this.path = path;
        this.pathIndex = 0;
    }

    public void followPath() {
        if (path == null || pathIndex >= path.size()) return;
        Point2D target = path.get(pathIndex);
        shape.setLayoutX(target.getX());
        shape.setLayoutY(target.getY());
        pathIndex++;
        updatePosition();
    }

    public boolean hasReachedStopLine() {
        Bounds b = shape.getBoundsInParent();
        return switch (direction) {
            case NORTH -> b.getMinY() <= Constants.STOP_LINE_NORTH_Y;
            case SOUTH -> b.getMaxY() >= Constants.STOP_LINE_SOUTH_Y;
            case EAST  -> b.getMaxX() >= Constants.STOP_LINE_EAST_X;
            case WEST  -> b.getMinX() <= Constants.STOP_LINE_WEST_X;
        };
    }

    public boolean hasPassedIntersection() {
        return isOutOfBounds(Constants.SIMULATION_WIDTH, Constants.SIMULATION_HEIGHT);
    }

    public boolean isOutOfBounds(double width, double height) {
        Bounds b = shape.getBoundsInParent();
        return b.getMaxX() < -50 || b.getMinX() > width + 50
                || b.getMaxY() < -50 || b.getMinY() > height + 50;
    }

    public boolean checkCollisionWith(Vehicle other) {
        return shape.getBoundsInParent().intersects(other.shape.getBoundsInParent());
    }

    public double distanceTo(Vehicle other) {
        return Math.hypot(this.x - other.x, this.y - other.y);
    }

    // Getters
    public Direction getDirection() { return direction; }
    public Lane getLane() { return lane; }
    public Turn getTurn() { return turn; }
    public VehicleType getType() { return type; }
    public Group getShape() { return shape; }
    public double getSpeed() { return speed; }

    public void setEnqueueTime(long timeMillis) { this.enqueueTimeMillis = timeMillis; }
    public long getEnqueueTime() { return enqueueTimeMillis; }
}
