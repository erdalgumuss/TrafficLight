package com.traffic.controller;

import com.traffic.model.Direction;

import java.util.EnumMap;
import java.util.Map;

/**
 * Simülasyon boyunca geçen araç sayılarını ve bekleme sürelerini takip eder.
 * Her yön için ortalama bekleme süresi hesaplanabilir.
 */
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

    /** Belirli bir yön için geçen aracı ve bekleme süresini kaydeder. */
    public void recordPass(Direction dir, int waitTimeInSeconds) {
        totalVehiclesPassed++;
        directionVehicleCount.put(dir, directionVehicleCount.get(dir) + 1);
        directionWaitTime.put(dir, directionWaitTime.get(dir) + waitTimeInSeconds);
    }

    /** Toplam geçen araç sayısı */
    public int getTotalVehiclesPassed() {
        return totalVehiclesPassed;
    }

    /** Belirli bir yön için geçen araç sayısı */
    public int getVehiclesPassed(Direction dir) {
        return directionVehicleCount.getOrDefault(dir, 0);
    }

    /** Belirli bir yön için ortalama bekleme süresi */
    public double getAverageWaitTime(Direction dir) {
        int count = directionVehicleCount.getOrDefault(dir, 0);
        if (count == 0) return 0;
        return (double) directionWaitTime.get(dir) / count;
    }

    /** Tüm istatistikleri sıfırlar (reset simülasyonu için) */
    public void reset() {
        totalVehiclesPassed = 0;
        for (Direction dir : Direction.values()) {
            directionVehicleCount.put(dir, 0);
            directionWaitTime.put(dir, 0);
        }
    }

    /** (İsteğe bağlı) Konsol çıktısı için özet istatistik */
    public String summary() {
        StringBuilder sb = new StringBuilder("=== Trafik Simülasyonu Özeti ===\n");
        for (Direction dir : Direction.values()) {
            sb.append(dir)
                    .append(" → Geçen: ").append(getVehiclesPassed(dir))
                    .append(" | Ortalama Bekleme: ")
                    .append(String.format("%.2f", getAverageWaitTime(dir)))
                    .append(" sn\n");
        }
        sb.append("Toplam Araç: ").append(getTotalVehiclesPassed()).append("\n");
        return sb.toString();
    }
}
