package com.traffic.view.status;

import com.traffic.model.Direction;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.EnumMap;
import java.util.Map;

public class TimeIndicatorPanel {
    private final Map<Direction, Label> timeLabels = new EnumMap<>(Direction.class);
    private final VBox panel;

    public TimeIndicatorPanel() {
        panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.getStyleClass().add("panel"); // 👈 CSS ile kart görünümü

        for (Direction dir : Direction.values()) {
            Label label = new Label(formatLabelText(dir, 0, "RED"));
            label.getStyleClass().add("label"); // 👈 Genel etiket stili
            setLabelColor(label, "RED");        // 👈 Başlangıç rengi
            timeLabels.put(dir, label);
            panel.getChildren().add(label);
        }
    }

    public VBox getPanel() {
        return panel;
    }

    public void updateTime(Direction dir, String text) {
        Label label = timeLabels.get(dir);
        if (label != null) {
            label.setText(text);
        }
    }

    public void updateTime(Direction dir, int seconds, String color) {
        Label label = timeLabels.get(dir);
        if (label != null) {
            label.setText(formatLabelText(dir, seconds, color));
            setLabelColor(label, color);
        }
    }

    public void reset() {
        for (Direction dir : Direction.values()) {
            updateTime(dir, 0, "RED");
        }
    }

    public void clear() {
        reset();
    }

    private String formatLabelText(Direction dir, int seconds, String color) {
        return String.format("%-6s → %s (%ds)", dir.name(), color, seconds);
    }

    private void setLabelColor(Label label, String color) {
        switch (color.toUpperCase()) {
            case "GREEN" -> label.setStyle("-fx-text-fill: green;");
            case "YELLOW" -> label.setStyle("-fx-text-fill: orange;");
            case "RED" -> label.setStyle("-fx-text-fill: red;");
            default -> label.setStyle("-fx-text-fill: black;");
        }
    }
}
