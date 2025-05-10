// TrafficInputPanel.java
package com.traffic.view.input;

import com.traffic.model.Direction;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
            Label label = new Label(dir.name() + " Araç:");
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

        panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-border-radius: 5px;");
        panel.getChildren().addAll(inputGrid, randomButton);
    }

    public VBox getPanel() {
        return panel;
    }

    public Map<Direction, Integer> getDensityValues() {
        Map<Direction, Integer> densityMap = new EnumMap<>(Direction.class);
        for (Direction dir : Direction.values()) {
            try {
                int count = Integer.parseInt(densityInputs.get(dir).getText());
                if (count < 0) count = 0;
                densityMap.put(dir, count);
            } catch (NumberFormatException e) {
                densityMap.put(dir, 0);
                System.out.println("Geçersiz sayı girdisi: " + dir.name());
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