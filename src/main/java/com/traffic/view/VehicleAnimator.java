package com.traffic.view;

import com.traffic.model.Vehicle;
import com.traffic.util.Constants;
import com.traffic.view.animation.PathFactory;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.*;

public class VehicleAnimator {
    private final Pane simulationPane;

    // Sadece düz giden araçlar burada izlenir
    private final List<Vehicle> straightMovingVehicles = new ArrayList<>();

    // Dönüş animasyonundaki araçlar ayrı tutulur
    private final Set<Vehicle> turningVehicles = new HashSet<>();

    public VehicleAnimator(Pane simulationPane) {
        this.simulationPane = simulationPane;
        startStraightVehicleLoop();
    }

    public void spawnVehicle(Vehicle vehicle) {
        double spawnX = vehicle.getLane().getSpawnPoint().getX();
        double spawnY = vehicle.getLane().getSpawnPoint().getY();

        vehicle.getShape().setLayoutX(spawnX);
        vehicle.getShape().setLayoutY(spawnY);

        simulationPane.getChildren().add(vehicle.getShape());

        if (vehicle.getTurn() == Vehicle.Turn.STRAIGHT) {
            straightMovingVehicles.add(vehicle);
        } else {
            animateTurn(vehicle);
        }
    }

    private void animateTurn(Vehicle vehicle) {
        Path path = PathFactory.createTurnPath(vehicle);

        PathTransition transition = new PathTransition();
        transition.setNode(vehicle.getShape());
        transition.setPath(path);
        transition.setDuration(Duration.seconds(3 + Math.random() * 1.5));
        transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        turningVehicles.add(vehicle);

        transition.setOnFinished(e -> {
            simulationPane.getChildren().remove(vehicle.getShape());
            turningVehicles.remove(vehicle);
        });

        transition.play();
    }

    private void startStraightVehicleLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Iterator<Vehicle> iterator = straightMovingVehicles.iterator();
                while (iterator.hasNext()) {
                    Vehicle v = iterator.next();

                    boolean atStop = v.hasReachedStopLine();
                    boolean clearPath = true;

                    Vehicle front = findFrontVehicle(v);
                    if (front != null && v.distanceTo(front) < Constants.SAFE_DISTANCE) {
                        clearPath = false;
                    }

                    if (!atStop || clearPath) {
                        v.move();
                    }

                    double x = v.getShape().getLayoutX();
                    double y = v.getShape().getLayoutY();
                    if (x < -100 || x > Constants.SIMULATION_WIDTH + 100
                            || y < -100 || y > Constants.SIMULATION_HEIGHT + 100) {
                        simulationPane.getChildren().remove(v.getShape());
                        iterator.remove();
                    }
                }
            }
        };

        timer.start();
    }

    private Vehicle findFrontVehicle(Vehicle current) {
        return straightMovingVehicles.stream()
                .filter(other -> other != current
                        && other.getDirection() == current.getDirection()
                        && !other.hasPassedIntersection())
                .min(Comparator.comparingDouble(current::distanceTo))
                .orElse(null);
    }

    public boolean willCollide(Vehicle newVehicle) {
        return straightMovingVehicles.stream().anyMatch(existing -> existing.checkCollisionWith(newVehicle));
    }

    public List<Vehicle> getActiveVehicles() {
        List<Vehicle> all = new ArrayList<>(straightMovingVehicles);
        all.addAll(turningVehicles);
        return all;
    }
}
