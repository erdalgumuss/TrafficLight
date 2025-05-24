package com.traffic.controller;

import com.traffic.model.Direction;
import com.traffic.model.TrafficLight;
import com.traffic.view.VehicleAnimator;

import java.util.EnumMap;
import java.util.Map;

public class SimulationManager {
    private final Map<Direction, TrafficLight> trafficLights = new EnumMap<>(Direction.class);
    private final TrafficLightController lightController;
    private final TrafficSimulationEngine simulationEngine;
    private final VehicleAnimator vehicleAnimator;

    public SimulationManager(
            VehicleAnimator animator,
            Map<Direction, Integer> inputVehicleCounts,
            TrafficSimulationEngine.TrafficUpdateCallback callback
    ) {
        this.vehicleAnimator = animator;
        this.lightController = new TrafficLightController();

        // 1) Işık nesnelerini oluştur
        for (Direction dir : Direction.values()) {
            trafficLights.put(dir, new TrafficLight(dir));
        }

        // 2) Kullanıcıdan gelen yoğunluk bilgisine göre yeşil süreleri hesapla
        Map<Direction, Integer> greenDurations =
                lightController.calculateGreenDurations(inputVehicleCounts);

        // 3) Tüm ışıkları çalıştır (bağımsız zamanlayıcılarla)
        lightController.startAllLights(trafficLights, greenDurations);

        // 4) Simülasyon motorunu oluştur (araç üretimi ve animasyon yönetimi içerir)
        this.simulationEngine = new TrafficSimulationEngine(
                trafficLights,
                greenDurations,
                animator,
                callback
        );
    }

    public void start() {
        simulationEngine.start();
    }

    public void pause() {
        simulationEngine.pause();
    }

    public void stop() {
        simulationEngine.stop();
    }

    public void reset() {
        simulationEngine.reset();
    }

    public SimulationStats getStats() {
        return simulationEngine.getStats();
    }
}
