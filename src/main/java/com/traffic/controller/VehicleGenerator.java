package com.traffic.controller;

import com.traffic.factory.VehicleFactory;
import com.traffic.model.Direction;
import com.traffic.model.Lane;
import com.traffic.model.Vehicle;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class VehicleGenerator {

    public Map<Direction, Queue<Vehicle>> generateVehicles(Map<Direction, Integer> vehicleCounts) {
        Map<Direction, Queue<Vehicle>> vehicleQueues = new EnumMap<>(Direction.class);

        for (Direction dir : Direction.values()) {
            int count = vehicleCounts.getOrDefault(dir, 0);
            Queue<Vehicle> queue = new LinkedList<>();

            for (int i = 0; i < count; i++) {
                Lane lane = Lane.getRandomLaneFor(dir);
                Vehicle vehicle = VehicleFactory.create(dir, lane);  // ✔ Factory kullanımı
                vehicle.setEnqueueTime(System.currentTimeMillis()); // ⏱ zaman damgası
                queue.add(vehicle);
            }

            vehicleQueues.put(dir, queue);
        }

        return vehicleQueues;
    }
}
