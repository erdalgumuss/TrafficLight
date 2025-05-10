package com.traffic.controller;

import com.traffic.model.Direction;
import com.traffic.util.Constants;

import java.util.EnumMap;
import java.util.Map;

public class TrafficLightController {

    /**
     * Araç sayılarına göre her yön için dinamik yeşil ışık sürelerini hesaplar.
     * @param vehicleCounts Kullanıcı tarafından girilen yoğunluk verisi
     * @return Yönlere göre atanacak yeşil süreler (saniye cinsinden)
     */
    public Map<Direction, Integer> calculateGreenDurations(Map<Direction, Integer> vehicleCounts) {
        Map<Direction, Integer> greenDurations = new EnumMap<>(Direction.class);

        Direction[] directions = Direction.values();
        int totalVehicles = vehicleCounts.values().stream().mapToInt(Integer::intValue).sum();
        int availableGreenTime = Constants.TOTAL_CYCLE_TIME - (Constants.YELLOW_DURATION * directions.length);

        int sumAssigned = 0;

        for (int i = 0; i < directions.length; i++) {
            Direction dir = directions[i];
            int count = vehicleCounts.getOrDefault(dir, 0);
            int greenTime;

            if (totalVehicles == 0) {
                greenTime = Constants.MIN_GREEN_DURATION;
            } else if (i == directions.length - 1) {
                // Son yöne kalan süreyi ver, yuvarlama hatasını engelle
                greenTime = availableGreenTime - sumAssigned;
                greenTime = Math.max(Constants.MIN_GREEN_DURATION, Math.min(Constants.MAX_GREEN_DURATION, greenTime));
            } else {
                double ratio = (double) count / totalVehicles;
                greenTime = (int) (ratio * availableGreenTime);
                greenTime = Math.max(Constants.MIN_GREEN_DURATION, Math.min(Constants.MAX_GREEN_DURATION, greenTime));
                sumAssigned += greenTime;
            }

            greenDurations.put(dir, greenTime);
        }

        return greenDurations;
    }
}