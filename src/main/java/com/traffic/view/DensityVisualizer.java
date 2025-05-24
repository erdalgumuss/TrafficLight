package com.traffic.view;

import com.traffic.model.Direction;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.EnumMap;
import java.util.Map;

public class DensityVisualizer {
    private final VBox panel;
    private final Map<Direction, Rectangle> bars = new EnumMap<>(Direction.class);
    private final Map<Direction, Label> labels = new EnumMap<>(Direction.class);

    public DensityVisualizer() {
        panel = new VBox(10);
        panel.setAlignment(Pos.CENTER_LEFT);
        panel.setPadding(new Insets(10));
        panel.getStyleClass().add("panel"); // ✅ Panel CSS entegrasyonu

        for (Direction dir : Direction.values()) {
            Label label = new Label(dir.name() + ": 0");
            label.setPrefWidth(60); // hizalama için sabit genişlik
            label.setStyle("-fx-font-family: Consolas; -fx-font-size: 13;");

            Rectangle bar = new Rectangle(0, 20);
            bar.setStroke(Color.BLACK);
            bar.getStyleClass().add("density-bar"); // ✅ CSS sınıfı uygulandı

            bars.put(dir, bar);
            labels.put(dir, label);

            HBox row = new HBox(10, label, bar);
            row.setAlignment(Pos.CENTER_LEFT);
            panel.getChildren().add(row);
        }
    }

    public void update(Map<Direction, Integer> densities) {
        int max = densities.values().stream().mapToInt(i -> i).max().orElse(1);
        for (Direction dir : Direction.values()) {
            int value = densities.getOrDefault(dir, 0);
            double ratio = (double) value / max;

            Rectangle bar = bars.get(dir);
            Label label = labels.get(dir);

            bar.setWidth(200 * ratio);
            label.setText(dir.name() + ": " + value);
        }
    }

    public Region getPanel() {
        return panel;
    }

    public void clear() {
        for (Direction dir : Direction.values()) {
            bars.get(dir).setWidth(0);
            labels.get(dir).setText(dir.name() + ": 0");
        }
    }
}
