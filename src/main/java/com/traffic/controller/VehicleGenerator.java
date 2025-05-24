package com.traffic.controller;

import com.traffic.factory.VehicleFactory;
import com.traffic.model.Direction;
import com.traffic.model.Vehicle;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Her yön için belirli sayıda araç üretip kuyruklara ekler.
 * Bu sınıf, simülasyon başında sabit sayıda araç üretimi yapar.
 */
public class VehicleGenerator {

    /**
     * Araç yoğunluk verisine göre yön bazlı kuyrukları oluşturur.
     *
     * @param vehicleCounts Her yön için araç sayısı
     * @return Yönlere karşılık gelen araç kuyrukları
     */
    public Map<Direction, Queue<Vehicle>> generateVehicles(Map<Direction, Integer> vehicleCounts) {
        Map<Direction, Queue<Vehicle>> vehicleQueues = new EnumMap<>(Direction.class);

        for (Direction dir : Direction.values()) {
            int count = vehicleCounts.getOrDefault(dir, 0);
            Queue<Vehicle> queue = new LinkedList<>();

            for (int i = 0; i < count; i++) {
                Vehicle vehicle = VehicleFactory.createVehicle(dir); // Basitleştirilmiş factory kullanımı
                vehicle.setEnqueueTime(System.currentTimeMillis()); // ⏱ Bekleme süresi istatistikleri için
                queue.add(vehicle);
            }

            vehicleQueues.put(dir, queue);
        }

        return vehicleQueues;
    }
}
