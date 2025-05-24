package com.traffic.view.status;

import com.traffic.controller.SimulationStats;
import com.traffic.model.Direction;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StatsPanel {
    private final VBox panel;
    private final Label totalPassedLabel;
    private final Label[] directionLabels;

    public StatsPanel() {
        panel = new VBox(15);
        panel.setPadding(new Insets(12));
        panel.getStyleClass().add("panel"); // CSS panel stili

        // Başlık
        Label title = new Label("📊 Simülasyon İstatistikleri");
        title.getStyleClass().add("title-label");
        panel.getChildren().add(title);

        // Toplam geçen araç
        totalPassedLabel = new Label("Toplam Geçen Araç: 0");
        totalPassedLabel.getStyleClass().add("label"); // isteğe bağlı sade stil
        panel.getChildren().add(totalPassedLabel);

        // Her yön için istatistik etiketi
        directionLabels = new Label[Direction.values().length];
        int i = 0;
        for (Direction dir : Direction.values()) {
            Label label = new Label(formatDirectionText(dir, 0, 0.0));
            label.getStyleClass().add("label"); // genel görünüm için
            directionLabels[i++] = label;
            panel.getChildren().add(label);
        }
    }

    public void update(SimulationStats stats) {
        totalPassedLabel.setText("Toplam Geçen Araç: " + stats.getTotalVehiclesPassed());

        int i = 0;
        for (Direction dir : Direction.values()) {
            int count = stats.getVehiclesPassed(dir);
            double avgWait = stats.getAverageWaitTime(dir);
            directionLabels[i++].setText(formatDirectionText(dir, count, avgWait));
        }
    }

    public void clear() {
        totalPassedLabel.setText("Toplam Geçen Araç: 0");
        int i = 0;
        for (Direction dir : Direction.values()) {
            directionLabels[i++].setText(formatDirectionText(dir, 0, 0.0));
        }
    }

    public VBox getPanel() {
        return panel;
    }

    private String formatDirectionText(Direction dir, int count, double avgWait) {
        return String.format("%-6s: %3d araç  |  Ort. Bekleme: %5.1f sn", dir.name(), count, avgWait);
    }
}
