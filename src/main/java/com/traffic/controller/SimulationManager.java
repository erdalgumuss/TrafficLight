package com.traffic.controller;

import com.traffic.model.Direction;
import com.traffic.model.TrafficLight;
import com.traffic.model.Vehicle;
import com.traffic.view.VehicleAnimator;
import javafx.scene.layout.Pane;

import java.util.EnumMap;
import java.util.Map;

public class SimulationManager {
    private final Map<Direction, TrafficLight> trafficLights = new EnumMap<>(Direction.class);
    private final VehicleQueueManager queueManager;
    private final TrafficLightController lightController;
    private final VehicleGenerator vehicleGenerator;
    private final TrafficSimulationEngine simulationEngine;

    public SimulationManager(Pane simulationPane,
                             VehicleAnimator animator,
                             Map<Direction, Integer> inputVehicleCounts,
                             VehicleQueueManager queueManager,
                             TrafficSimulationEngine.TrafficUpdateCallback callback) {
        this.queueManager      = queueManager;
        this.lightController   = new TrafficLightController();
        this.vehicleGenerator  = new VehicleGenerator();

        // 1) Trafik ışıkları oluştur
        for (Direction dir : Direction.values()) {
            trafficLights.put(dir, new TrafficLight(dir));
        }

        // 2) Yeşil sürelerini hesapla
        Map<Direction, Integer> greenDurations =
                lightController.calculateGreenDurations(inputVehicleCounts);

        // 3) Simülasyon motorunu başlat, animatörü doğrudan geçir
        this.simulationEngine = new TrafficSimulationEngine(
                trafficLights,
                queueManager,
                greenDurations,
                animator,
                callback
        );
    }

    public void start(Map<Direction, Integer> inputVehicleCounts) {
        // Kuyrukları sıfırla ve yeniden oluştur
        queueManager.clearAll();
        var newQueues = vehicleGenerator.generateVehicles(inputVehicleCounts);
        queueManager.setAllQueues(newQueues);

        // Bu kısmı kaldır:
        // var animator = simulationEngine.getAnimator();
        // for (var entry : newQueues.entrySet()) {
        //     for (Vehicle vehicle : entry.getValue()) {
        //         animator.spawnVehicle(vehicle);
        //     }
        // }

        // Simülasyonu çalıştır
        simulationEngine.start();
    }


    public void pause()   { simulationEngine.pause(); }
    public void stop()    { simulationEngine.stop(); }
    public void reset()   { simulationEngine.reset(); }
    public SimulationStats getStats() { return simulationEngine.getStats(); }
}
