package com.traffic.model;

import com.traffic.util.Constants;
import javafx.geometry.Point2D;

public enum Lane {
    LEFT(Direction.NORTH, new Point2D(Constants.CENTER_X - 20, Constants.SIMULATION_HEIGHT + 50), Vehicle.Turn.LEFT),
    RIGHT(Direction.NORTH, new Point2D(Constants.CENTER_X + 20, Constants.SIMULATION_HEIGHT + 50), Vehicle.Turn.RIGHT),

    LEFT_S(Direction.SOUTH, new Point2D(Constants.CENTER_X + 20, -50), Vehicle.Turn.LEFT),
    RIGHT_S(Direction.SOUTH, new Point2D(Constants.CENTER_X - 20, -50), Vehicle.Turn.RIGHT),

    LEFT_E(Direction.EAST, new Point2D(-50, Constants.CENTER_Y - 20), Vehicle.Turn.LEFT),
    RIGHT_E(Direction.EAST, new Point2D(-50, Constants.CENTER_Y + 20), Vehicle.Turn.RIGHT),

    LEFT_W(Direction.WEST, new Point2D(Constants.SIMULATION_WIDTH + 50, Constants.CENTER_Y + 20), Vehicle.Turn.LEFT),
    RIGHT_W(Direction.WEST, new Point2D(Constants.SIMULATION_WIDTH + 50, Constants.CENTER_Y - 20), Vehicle.Turn.RIGHT);

    private final Direction direction;
    private final Point2D spawnPoint;
    private final Vehicle.Turn preferredTurn;

    Lane(Direction direction, Point2D spawnPoint, Vehicle.Turn preferredTurn) {
        this.direction = direction;
        this.spawnPoint = spawnPoint;
        this.preferredTurn = preferredTurn;
    }

    public Direction getDirection() {
        return direction;
    }

    public Point2D getSpawnPoint() {
        return spawnPoint;
    }

    public Vehicle.Turn getPreferredTurn() {
        return preferredTurn;
    }

    public static Lane getRandomLaneFor(Direction dir) {
        return switch (dir) {
            case NORTH -> Math.random() < 0.5 ? LEFT : RIGHT;
            case SOUTH -> Math.random() < 0.5 ? LEFT_S : RIGHT_S;
            case EAST  -> Math.random() < 0.5 ? LEFT_E : RIGHT_E;
            case WEST  -> Math.random() < 0.5 ? LEFT_W : RIGHT_W;
        };
    }
}
