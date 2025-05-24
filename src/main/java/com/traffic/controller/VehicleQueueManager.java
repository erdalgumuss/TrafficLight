//package com.traffic.controller;
//
//import com.traffic.model.Direction;
//import com.traffic.model.Vehicle;
//
//import java.util.EnumMap;
//import java.util.LinkedList;
//import java.util.Map;
//import java.util.Queue;
//
///**
// * Her yön için araç kuyruklarını yöneten sınıf.
// * Sadece mevcut araçları simülasyon başında yönlere göre sıraya alır,
// * daha sonra sıradan çıkararak simülasyonu yürütür.
// */
//public class VehicleQueueManager {
//    private final Map<Direction, Queue<Vehicle>> vehicleQueues;
//
//    public VehicleQueueManager() {
//        vehicleQueues = new EnumMap<>(Direction.class);
//        for (Direction dir : Direction.values()) {
//            vehicleQueues.put(dir, new LinkedList<>());
//        }
//    }
//
//    /** Belirli yöne araç ekler. */
//    public void addVehicle(Direction dir, Vehicle vehicle) {
//        vehicleQueues.get(dir).add(vehicle);
//    }
//
//    /** Belirli yöndeki ilk aracı sıradan çıkarır. */
//    public Vehicle pollVehicle(Direction dir) {
//        return vehicleQueues.get(dir).poll();
//    }
//
//    /** Belirli yöndeki sıradaki aracı getirir. */
//    public Vehicle peekVehicle(Direction dir) {
//        return vehicleQueues.get(dir).peek();
//    }
//
//    /** Belirli yönün kuyruğunu verir. */
//    public Queue<Vehicle> getQueue(Direction dir) {
//        return vehicleQueues.get(dir);
//    }
//
//    /** Tüm kuyrukları temizler. */
//    public void clearAll() {
//        for (Queue<Vehicle> queue : vehicleQueues.values()) {
//            queue.clear();
//        }
//    }
//
//    /** Belirli yönün kuyruğu boş mu? */
//    public boolean isEmpty(Direction dir) {
//        Queue<Vehicle> q = vehicleQueues.get(dir);
//        return q == null || q.isEmpty();
//    }
//}
