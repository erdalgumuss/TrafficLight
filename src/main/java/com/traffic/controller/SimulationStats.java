package com.traffic.controller;

import com.traffic.model.Direction;

import java.util.EnumMap;
import java.util.Map;

public class SimulationStats {
    private int totalVehiclesPassed = 0;
    private final Map<Direction, Integer> directionVehicleCount = new EnumMap<>(Direction.class);
    private final Map<Direction, Integer> directionWaitTime = new EnumMap<>(Direction.class);

    public SimulationStats() {
        for (Direction dir : Direction.values()) {
            directionVehicleCount.put(dir, 0);
            directionWaitTime.put(dir, 0);
        }
    }

    public void recordPass(Direction dir, int waitTimeInSeconds) {
        totalVehiclesPassed++;
        directionVehicleCount.put(dir, directionVehicleCount.get(dir) + 1);
        directionWaitTime.put(dir, directionWaitTime.get(dir) + waitTimeInSeconds);
    }

    public void incrementVehicleCount(Direction dir) {
        recordPass(dir, 0); // geçici kullanım
    }

    public int getTotalVehiclesPassed() {
        return totalVehiclesPassed;
    }

    public int getVehiclesPassed(Direction dir) {
        return directionVehicleCount.getOrDefault(dir, 0);
    }

    public double getAverageWaitTime(Direction dir) {
        int count = directionVehicleCount.getOrDefault(dir, 0);
        if (count == 0) return 0;
        return (double) directionWaitTime.get(dir) / count;
    }

    public Map<Direction, Integer> getDirectionVehicleCount() {
        return directionVehicleCount;
    }

    public Map<Direction, Integer> getDirectionWaitTime() {
        return directionWaitTime;
    }

    public void reset() {
        totalVehiclesPassed = 0;
        for (Direction dir : Direction.values()) {
            directionVehicleCount.put(dir, 0);
            directionWaitTime.put(dir, 0);
        }
    }
}
