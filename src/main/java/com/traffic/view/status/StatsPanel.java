package com.traffic.view.status;

import com.traffic.controller.SimulationStats;
import com.traffic.model.Direction;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class StatsPanel {
    private final VBox panel;
    private final Label totalPassedLabel;
    private final Label[] directionLabels;

    public StatsPanel() {
        panel = new VBox(15);
        panel.setPadding(new Insets(12));
        panel.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ccc; -fx-border-radius: 6; -fx-background-radius: 6;");

        // Başlık
        Label title = new Label("📊 Simülasyon İstatistikleri");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        title.setTextFill(Color.DARKSLATEGRAY);
        panel.getChildren().add(title);

        // Toplam
        totalPassedLabel = new Label("Toplam Geçen Araç: 0");
        totalPassedLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        totalPassedLabel.setTextFill(Color.BLACK);
        panel.getChildren().add(totalPassedLabel);

        // Yön başlıkları
        directionLabels = new Label[Direction.values().length];
        int i = 0;
        for (Direction dir : Direction.values()) {
            Label label = new Label(formatDirectionText(dir, 0, 0.0));
            label.setFont(Font.font("Consolas", 13));
            label.setTextFill(Color.DIMGRAY);
            directionLabels[i++] = label;
            panel.getChildren().add(label);
        }
    }

    public void update(SimulationStats stats) {
        int total = stats.getTotalVehiclesPassed();
        if (!totalPassedLabel.getText().endsWith(String.valueOf(total))) {
            totalPassedLabel.setText("Toplam Geçen Araç: " + total);
        }

        int i = 0;
        for (Direction dir : Direction.values()) {
            int count = stats.getVehiclesPassed(dir);
            double avgWait = stats.getAverageWaitTime(dir);
            directionLabels[i].setText(formatDirectionText(dir, count, avgWait));
            i++;
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
        return String.format("%-6s: %3d araç  |  Ortalama Bekleme: %4.1f sn", dir.name(), count, avgWait);
    }
}
