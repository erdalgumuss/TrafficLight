package com.traffic.view.animation;

import com.traffic.model.Direction;
import com.traffic.model.Vehicle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;

public class PathFactory {

    public static Path createTurnPath(Vehicle vehicle) {
        Direction dir = vehicle.getDirection();
        Vehicle.Turn turn = vehicle.getTurn();

        double x = vehicle.getShape().getLayoutX();
        double y = vehicle.getShape().getLayoutY();

        Path path = new Path();
        path.getElements().add(new MoveTo(x, y));

        switch (dir) {
            case NORTH -> {
                if (turn == Vehicle.Turn.RIGHT) {
                    path.getElements().add(new QuadCurveTo(320, 260, 480, 320));
                } else if (turn == Vehicle.Turn.LEFT) {
                    path.getElements().add(new QuadCurveTo(300, 200, 160, 320));
                }
            }
            case SOUTH -> {
                if (turn == Vehicle.Turn.RIGHT) {
                    path.getElements().add(new QuadCurveTo(320, 400, 160, 320));
                } else if (turn == Vehicle.Turn.LEFT) {
                    path.getElements().add(new QuadCurveTo(320, 300, 480, 160));
                }
            }
            case EAST -> {
                if (turn == Vehicle.Turn.RIGHT) {
                    path.getElements().add(new QuadCurveTo(400, 320, 320, 160));
                } else if (turn == Vehicle.Turn.LEFT) {
                    path.getElements().add(new QuadCurveTo(300, 320, 160, 480));
                }
            }
            case WEST -> {
                if (turn == Vehicle.Turn.RIGHT) {
                    path.getElements().add(new QuadCurveTo(240, 320, 320, 480));
                } else if (turn == Vehicle.Turn.LEFT) {
                    path.getElements().add(new QuadCurveTo(320, 320, 480, 160));
                }
            }
        }

        return path;
    }
}
