package com.traffic.view.input;

import com.traffic.model.Direction;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

public class TrafficInputPanel {
    private final Map<Direction, TextField> densityInputs = new EnumMap<>(Direction.class);
    private final VBox panel;

    public TrafficInputPanel() {
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setPadding(new Insets(10));

        int row = 0;
        for (Direction dir : Direction.values()) {
            Label label = new Label(dir.toString() + " yönü:");
            TextField input = new TextField();
            input.setPromptText("örn: 10");

            input.setTextFormatter(new TextFormatter<>(change -> {
                if (change.getText().matches("\\d*")) return change;
                return null;
            }));

            densityInputs.put(dir, input);
            inputGrid.add(label, 0, row);
            inputGrid.add(input, 1, row);
            row++;
        }

        Button randomButton = new Button("Rastgele Doldur");
        randomButton.setOnAction(e -> fillWithRandomData());

        panel = new VBox(10, inputGrid, randomButton);
        panel.setPadding(new Insets(10));
        panel.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-border-radius: 5px;");
    }

    public VBox getPanel() {
        return panel;
    }

    public Map<Direction, Integer> getDensityValues() {
        Map<Direction, Integer> densityMap = new EnumMap<>(Direction.class);
        for (Direction dir : Direction.values()) {
            try {
                int count = Integer.parseInt(densityInputs.get(dir).getText());
                densityMap.put(dir, Math.max(count, 0));
            } catch (NumberFormatException e) {
                densityMap.put(dir, 0);
                // UI alert eklenebilir
            }
        }
        return densityMap;
    }

    public void clearInputs() {
        densityInputs.values().forEach(field -> field.setText(""));
    }

    public void fillWithRandomData() {
        Random rand = new Random();
        for (Direction dir : Direction.values()) {
            densityInputs.get(dir).setText(String.valueOf(rand.nextInt(51)));
        }
    }
}
