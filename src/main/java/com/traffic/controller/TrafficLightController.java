package com.traffic.controller;

import com.traffic.model.Direction;
import com.traffic.model.TrafficLight;
import com.traffic.utils.Constants;

import java.util.EnumMap;
import java.util.Map;

public class TrafficLightController {

    public Map<Direction, Integer> calculateGreenDurations(Map<Direction, Integer> vehicleCounts) {
        Map<Direction, Integer> greenDurations = new EnumMap<>(Direction.class);
        Direction[] directions = Direction.values();

        int totalVehicles = vehicleCounts.values().stream().mapToInt(Integer::intValue).sum();
        int totalYellowTime = Constants.YELLOW_DURATION * directions.length;
        int availableGreenTime = Constants.TOTAL_CYCLE_TIME - totalYellowTime;

        int sumAssigned = 0;

        for (int i = 0; i < directions.length; i++) {
            Direction dir = directions[i];
            int vehicleCount = vehicleCounts.getOrDefault(dir, 0);
            int greenTime;

            if (totalVehicles == 0) {
                greenTime = Constants.MIN_GREEN_DURATION;
            } else if (i == directions.length - 1) {
                greenTime = availableGreenTime - sumAssigned;
                greenTime = clamp(greenTime, Constants.MIN_GREEN_DURATION, Constants.MAX_GREEN_DURATION);
            } else {
                double ratio = (double) vehicleCount / totalVehicles;
                greenTime = (int) (ratio * availableGreenTime);
                greenTime = clamp(greenTime, Constants.MIN_GREEN_DURATION, Constants.MAX_GREEN_DURATION);
                sumAssigned += greenTime;
            }

            greenDurations.put(dir, greenTime);
        }

        return greenDurations;
    }

    public void startAllLights(Map<Direction, TrafficLight> lights, Map<Direction, Integer> greenDurations) {
        for (Direction dir : Direction.values()) {
            int greenTime = greenDurations.getOrDefault(dir, Constants.MIN_GREEN_DURATION);
            lights.get(dir).startCycle(greenTime);
        }
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
