package com.traffic.view;

import com.traffic.model.Direction;
import com.traffic.model.Vehicle;
import com.traffic.utils.Constants;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

import java.util.*;

/**
 * Araçları sahnede yöneten animasyon sınıfı.
 * Her aracın yönüne göre ışık durumu kontrol edilir.
 */
public class VehicleAnimator {
    private final Pane simulationPane;
    private final List<Vehicle> activeVehicles = new ArrayList<>();
    private final Map<Direction, Boolean> greenLightStatus = new EnumMap<>(Direction.class);

    public VehicleAnimator(Pane simulationPane) {
        this.simulationPane = simulationPane;

        for (Direction dir : Direction.values()) {
            greenLightStatus.put(dir, false); // tüm yönler başlangıçta kırmızı
        }

        startAnimationLoop();
    }

    public void spawnVehicle(Vehicle vehicle) {
        simulationPane.getChildren().add(vehicle.getShape());
        activeVehicles.add(vehicle);
    }

    /**
     * Işık değişimini dışarıdan haber alan callback.
     */
    public void updateGreenLight(Direction dir, boolean isGreen) {
        greenLightStatus.put(dir, isGreen);
    }

    private void startAnimationLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Iterator<Vehicle> iterator = activeVehicles.iterator();
                while (iterator.hasNext()) {
                    Vehicle v = iterator.next();
                    Direction dir = v.getDirection();

                    boolean atStopLine = v.hasReachedStopLine();
                    boolean isGreen = greenLightStatus.getOrDefault(dir, false);

                    // Öndeki araçla güvenli mesafe kontrolü
                    Vehicle front = findFrontVehicle(v);
                    boolean hasSafeGap = front == null || v.distanceTo(front) >= Constants.SAFE_DISTANCE;

                    // Yeni karar mantığı
                    if (atStopLine) {
                        if (isGreen && hasSafeGap) {
                            v.move();
                        }
                    } else {
                        v.move(); // henüz durma çizgisine gelmedi, hareket etmeye devam et
                    }

                    // Ekran dışına çıktıysa temizle
                    if (v.hasPassedIntersection()) {
                        simulationPane.getChildren().remove(v.getShape());
                        iterator.remove();
                    }
                }
            }
        };
        timer.start();
    }

    private Vehicle findFrontVehicle(Vehicle current) {
        return activeVehicles.stream()
                .filter(other -> other != current
                        && other.getDirection() == current.getDirection()
                        && !other.hasPassedIntersection())
                .min(Comparator.comparingDouble(current::distanceTo))
                .orElse(null);
    }

    public boolean willCollide(Vehicle newVehicle) {
        return activeVehicles.stream()
                .anyMatch(existing -> existing.checkCollisionWith(newVehicle));
    }

    public List<Vehicle> getActiveVehicles() {
        return new ArrayList<>(activeVehicles);
    }
}
