package com.traffic.model;

import com.traffic.utils.Constants;
import javafx.geometry.Bounds;
import javafx.scene.Group;

public class Vehicle {
    public enum VehicleType { CAR, TRUCK, VAN }

    private final Direction direction;
    private final VehicleType type;
    private final Group shape;
    private final double speed;

    private long enqueueTimeMillis;
    private double x;
    private double y;

    public Vehicle(Direction direction, VehicleType type, Group shape, double speed) {
        this.direction = direction;
        this.type = type;
        this.shape = shape;
        this.speed = speed;
        updatePosition();
    }

    /**
     * Aracı kendi yönüne göre bir frame hareket ettirir.
     */
    public void move() {
        switch (direction) {
            case NORTH -> shape.setLayoutY(shape.getLayoutY() - speed);
            case SOUTH -> shape.setLayoutY(shape.getLayoutY() + speed);
            case EAST  -> shape.setLayoutX(shape.getLayoutX() + speed);
            case WEST  -> shape.setLayoutX(shape.getLayoutX() - speed);
        }
        updatePosition();
    }

    /**
     * Güncel konumunu merkezi (x,y) koordinatları olarak yeniler.
     */
    public void updatePosition() {
        Bounds b = shape.getBoundsInParent();
        this.x = b.getMinX() + b.getWidth() / 2.0;
        this.y = b.getMinY() + b.getHeight() / 2.0;
    }

    /**
     * Araç durma çizgisine geldi mi? (Işık kontrolü dışarıdan yapılır)
     */
    public boolean hasReachedStopLine() {
        Bounds b = shape.getBoundsInParent();
        return switch (direction) {
            case NORTH -> b.getMinY() <= Constants.STOP_LINE_NORTH_Y;
            case SOUTH -> b.getMaxY() >= Constants.STOP_LINE_SOUTH_Y;
            case EAST  -> b.getMaxX() >= Constants.STOP_LINE_EAST_X;
            case WEST  -> b.getMinX() <= Constants.STOP_LINE_WEST_X;
        };
    }

    /**
     * Araç simülasyon alanının dışına çıktı mı?
     */
    public boolean hasPassedIntersection() {
        return isOutOfBounds(Constants.SIMULATION_WIDTH, Constants.SIMULATION_HEIGHT);
    }

    public boolean isOutOfBounds(double width, double height) {
        Bounds b = shape.getBoundsInParent();
        return b.getMaxX() < -50 || b.getMinX() > width + 50
                || b.getMaxY() < -50 || b.getMinY() > height + 50;
    }

    public double distanceTo(Vehicle other) {
        return Math.hypot(this.x - other.x, this.y - other.y);
    }

    public boolean checkCollisionWith(Vehicle other) {
        return this.shape.getBoundsInParent().intersects(other.shape.getBoundsInParent());
    }

    // Getter & Setter
    public Direction getDirection() { return direction; }
    public VehicleType getType() { return type; }
    public Group getShape() { return shape; }
    public double getSpeed() { return speed; }

    public void setEnqueueTime(long timeMillis) { this.enqueueTimeMillis = timeMillis; }
    public long getEnqueueTime() { return enqueueTimeMillis; }
}
