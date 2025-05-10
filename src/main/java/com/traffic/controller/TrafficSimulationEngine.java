package com.traffic.controller;

import com.traffic.model.Direction;
import com.traffic.model.LightColor;
import com.traffic.model.TrafficLight;
import com.traffic.model.Vehicle;
import com.traffic.util.Constants;
import com.traffic.view.VehicleAnimator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Map;
import java.util.Queue;

public class TrafficSimulationEngine {
    private final Map<Direction, TrafficLight> trafficLights;
    private final VehicleQueueManager vehicleQueueManager;
    private final Map<Direction, Integer> greenDurations;
    private VehicleAnimator vehicleAnimator;
    private final TrafficUpdateCallback updateCallback;
    private final SimulationStats stats;

    private final Direction[] directionOrder = Direction.values();
    private int directionIndex = 0;
    private Direction currentDirection;

    private boolean isYellowPhase = false;
    private int yellowTimer = 0;
    private int vehicleSpawnCounter = 0;

    private Timeline simulationTimer;

    public interface TrafficUpdateCallback {
        void onTrafficLightUpdate(Direction dir, LightColor color, int remainingTime);
    }

    public TrafficSimulationEngine(Map<Direction, TrafficLight> trafficLights,
                                   VehicleQueueManager queueManager,
                                   Map<Direction, Integer> greenDurations,
                                   VehicleAnimator vehicleAnimator,
                                   TrafficUpdateCallback updateCallback) {
        this.trafficLights = trafficLights;
        this.vehicleQueueManager = queueManager;
        this.greenDurations = greenDurations;
        this.vehicleAnimator = vehicleAnimator;
        this.updateCallback = updateCallback;
        this.stats = new SimulationStats();
    }

    /** Animatöre erişim sağlayan getter */
    public VehicleAnimator getAnimator() {
        return vehicleAnimator;
    }

    /** Animatörü değiştirmek için setter */
    public void setAnimator(VehicleAnimator animator) {
        this.vehicleAnimator = animator;
    }

    public boolean isGreen(Direction dir) {
        TrafficLight light = trafficLights.get(dir);
        return light != null && light.isGreen();
    }

    public void start() {
        directionIndex    = 0;
        currentDirection  = directionOrder[directionIndex];
        setGreen(currentDirection);

        simulationTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> tick()));
        simulationTimer.setCycleCount(Timeline.INDEFINITE);
        simulationTimer.play();
    }

    public void pause() {
        if (simulationTimer != null) simulationTimer.pause();
    }

    public void stop() {
        if (simulationTimer != null) simulationTimer.stop();
    }

    public void reset() {
        stop();
        vehicleSpawnCounter = 0;
        directionIndex      = 0;
        isYellowPhase       = false;
        for (Direction dir : Direction.values()) {
            TrafficLight tl = trafficLights.get(dir);
            tl.setColor(LightColor.RED);
            tl.setRemainingTime(0);
        }
        stats.reset();
    }

    private void tick() {
        // (A) Işık yönetimi
        TrafficLight light = trafficLights.get(currentDirection);
        if (isYellowPhase) {
            if (--yellowTimer <= 0) {
                isYellowPhase    = false;
                directionIndex   = (directionIndex + 1) % directionOrder.length;
                currentDirection = directionOrder[directionIndex];
                setGreen(currentDirection);
            }
        } else {
            light.tick();
            if (light.isTimeUp()) setYellow(currentDirection);
        }

        // (B) Araç çıkışı
        vehicleSpawnCounter++;
        if (vehicleSpawnCounter % Constants.VEHICLE_SPAWN_RATE == 0 && vehicleAnimator != null) {
            Queue<Vehicle> queue = vehicleQueueManager.getQueue(currentDirection);
            if (queue != null && !queue.isEmpty()) {
                Vehicle vehicle = queue.peek();
                if (!vehicleAnimator.willCollide(vehicle)) {
                    vehicle = queue.poll();
                    long waitTime = (System.currentTimeMillis() - vehicle.getEnqueueTime()) / 1000;
                    stats.recordPass(currentDirection, (int) waitTime);
                    vehicleAnimator.spawnVehicle(vehicle);
                }
            }
        }

        // (C) UI güncelleme
        for (Direction dir : Direction.values()) {
            TrafficLight tl = trafficLights.get(dir);
            updateCallback.onTrafficLightUpdate(dir, tl.getColor(), tl.getRemainingTime());
        }
    }

    private void setGreen(Direction dir) {
        vehicleSpawnCounter = 0;
        trafficLights.values().forEach(tl -> {
            tl.setColor(LightColor.RED);
            tl.setRemainingTime(0);
        });
        int greenTime = greenDurations.getOrDefault(dir, Constants.MIN_GREEN_DURATION);
        TrafficLight tl = trafficLights.get(dir);
        tl.setColor(LightColor.GREEN);
        tl.setRemainingTime(greenTime);
    }

    private void setYellow(Direction dir) {
        isYellowPhase = true;
        yellowTimer   = Constants.YELLOW_DURATION;
        TrafficLight tl = trafficLights.get(dir);
        tl.setColor(LightColor.YELLOW);
        tl.setRemainingTime(yellowTimer);
    }

    public SimulationStats getStats() {
        return stats;
    }
}
