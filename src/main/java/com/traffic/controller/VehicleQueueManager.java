package com.traffic.controller;

import com.traffic.model.Direction;
import com.traffic.model.Vehicle;

import java.util.*;

/**
 * Her yön için araç kuyruklarını yöneten merkezi sınıf.
 */
public class VehicleQueueManager {
    private final Map<Direction, Queue<Vehicle>> vehicleQueues;

    public VehicleQueueManager() {
        vehicleQueues = new EnumMap<>(Direction.class);
        for (Direction dir : Direction.values()) {
            vehicleQueues.put(dir, new LinkedList<>());
        }
    }

    /**
     * Belirli bir yön için yeni kuyruk atar.
     */
    public void setQueue(Direction dir, Queue<Vehicle> queue) {
        vehicleQueues.put(dir, queue);
    }

    /**
     * Tüm yönlere ait kuyrukları bir defada günceller.
     */
    public void setAllQueues(Map<Direction, Queue<Vehicle>> allQueues) {
        vehicleQueues.clear();
        vehicleQueues.putAll(allQueues);
    }

    /**
     * Belirli yöne araç ekler.
     */
    public void addVehicle(Direction dir, Vehicle vehicle) {
        vehicleQueues.get(dir).add(vehicle);
    }

    /**
     * Belirli yöndeki ilk aracı sıradan çıkarır.
     */
    public Vehicle pollVehicle(Direction dir) {
        return vehicleQueues.get(dir).poll();
    }

    /**
     * Belirli yöndeki sıradaki ilk aracı getirir.
     */
    public Vehicle peekVehicle(Direction dir) {
        return vehicleQueues.get(dir).peek();
    }

    /**
     * Belirli yönün kuyruğunu verir.
     */
    public Queue<Vehicle> getQueue(Direction dir) {
        return vehicleQueues.get(dir);
    }

    /**
     * Tüm yönlere ait kuyrukları döner.
     */
    public Map<Direction, Queue<Vehicle>> getAllQueues() {
        return vehicleQueues;
    }

    /**
     * Tüm kuyrukları temizler.
     */
    public void clearAll() {
        for (Queue<Vehicle> queue : vehicleQueues.values()) {
            queue.clear();
        }
    }

    /**
     * Belirli yönün kuyruğu boş mu?
     */
    public boolean isEmpty(Direction dir) {
        Queue<Vehicle> q = vehicleQueues.get(dir);
        return q == null || q.isEmpty();
    }
}
