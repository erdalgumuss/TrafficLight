package com.traffic.controller;

import com.traffic.factory.VehicleFactory;
import com.traffic.model.Direction;
import com.traffic.model.LightColor;
import com.traffic.model.TrafficLight;
import com.traffic.model.Vehicle;
import com.traffic.utils.Constants;
import com.traffic.view.VehicleAnimator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

public class TrafficSimulationEngine {

    public interface TrafficUpdateCallback {
        void onTrafficLightUpdate(Direction dir, LightColor color, int remainingTime);
    }

    private final Map<Direction, TrafficLight> trafficLights = new EnumMap<>(Direction.class);
    private final Map<Direction, Integer> greenDurations;
    private final TrafficUpdateCallback updateCallback;
    private VehicleAnimator vehicleAnimator;

    private final SimulationStats stats = new SimulationStats();

    private final Direction[] directionOrder = Direction.values();
    private int directionIndex = 0;
    private Direction currentDirection;

    private boolean isYellowPhase = false;
    private int yellowTimer = 0;

    private Timeline lightTimer;
    private Timeline vehicleSpawnTimer;

    public TrafficSimulationEngine(Map<Direction, TrafficLight> trafficLights,
                                   Map<Direction, Integer> greenDurations,
                                   VehicleAnimator vehicleAnimator,
                                   TrafficUpdateCallback updateCallback) {
        this.trafficLights.putAll(trafficLights);
        this.greenDurations = greenDurations;
        this.vehicleAnimator = vehicleAnimator;
        this.updateCallback = updateCallback;
    }

    public void start() {
        directionIndex = 0;
        currentDirection = directionOrder[directionIndex];
        setGreen(currentDirection);

        lightTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> tick()));
        lightTimer.setCycleCount(Timeline.INDEFINITE);
        lightTimer.play();

        vehicleSpawnTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> spawnRandomVehicle()));
        vehicleSpawnTimer.setCycleCount(Timeline.INDEFINITE);
        vehicleSpawnTimer.play();
    }

    public void pause() {
        if (lightTimer != null) lightTimer.pause();
        if (vehicleSpawnTimer != null) vehicleSpawnTimer.pause();
    }

    public void stop() {
        if (lightTimer != null) lightTimer.stop();
        if (vehicleSpawnTimer != null) vehicleSpawnTimer.stop();
    }

    public void reset() {
        stop();
        directionIndex = 0;
        isYellowPhase = false;
        for (Direction dir : Direction.values()) {
            TrafficLight tl = trafficLights.get(dir);
            tl.setColor(LightColor.RED);
            tl.setRemainingTime(0);
            vehicleAnimator.updateGreenLight(dir, false);
        }
        stats.reset();
    }

    private void tick() {
        updateSignalTiming();
        updateUI();
    }

    private void updateSignalTiming() {
        TrafficLight light = trafficLights.get(currentDirection);
        if (isYellowPhase) {
            if (--yellowTimer <= 0) {
                isYellowPhase = false;
                currentDirection = nextDirection();
                setGreen(currentDirection);
            }
        } else {
            light.tick();
            if (light.isTimeUp()) {
                setYellow(currentDirection);
            }
        }
    }

    private Direction nextDirection() {
        directionIndex = (directionIndex + 1) % directionOrder.length;
        return directionOrder[directionIndex];
    }

    private void setGreen(Direction dir) {
        trafficLights.values().forEach(tl -> {
            tl.setColor(LightColor.RED);
            tl.setRemainingTime(0);
        });
        int greenTime = greenDurations.getOrDefault(dir, Constants.MIN_GREEN_DURATION);
        TrafficLight tl = trafficLights.get(dir);
        tl.setColor(LightColor.GREEN);
        tl.setRemainingTime(greenTime);
        vehicleAnimator.updateGreenLight(dir, true);
    }

    private void setYellow(Direction dir) {
        isYellowPhase = true;
        yellowTimer = Constants.YELLOW_DURATION;
        TrafficLight tl = trafficLights.get(dir);
        tl.setColor(LightColor.YELLOW);
        tl.setRemainingTime(yellowTimer);
        vehicleAnimator.updateGreenLight(dir, false);
    }

    private void updateUI() {
        for (Direction dir : Direction.values()) {
            TrafficLight tl = trafficLights.get(dir);
            updateCallback.onTrafficLightUpdate(dir, tl.getColor(), tl.getRemainingTime());
        }
    }

    private void spawnRandomVehicle() {
        Direction[] directions = Direction.values();
        Direction dir = directions[new Random().nextInt(directions.length)];
        Vehicle vehicle = VehicleFactory.createVehicle(dir);
        vehicle.setEnqueueTime(System.currentTimeMillis());
        vehicleAnimator.spawnVehicle(vehicle);
    }

    public void setAnimator(VehicleAnimator animator) {
        this.vehicleAnimator = animator;
    }

    public VehicleAnimator getAnimator() {
        return vehicleAnimator;
    }

    public SimulationStats getStats() {
        return stats;
    }
}
