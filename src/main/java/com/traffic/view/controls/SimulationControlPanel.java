// SimulationControlPanel.java
package com.traffic.view.controls;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SimulationControlPanel {
    private final VBox panel;
    private final Button startButton;
    private final Button pauseButton;
    private final Button resetButton;
    private final Button stopButton;

    public SimulationControlPanel() {
        startButton = new Button("Start");
        pauseButton = new Button("Pause");
        resetButton = new Button("Reset");
        stopButton = new Button("Stop");

        startButton.getStyleClass().add("btn-start");
        pauseButton.getStyleClass().add("btn-pause");
        resetButton.getStyleClass().add("btn-reset");
        stopButton.getStyleClass().add("btn-stop");

        HBox buttonBox = new HBox(10, startButton, pauseButton, resetButton, stopButton);
        buttonBox.setAlignment(Pos.CENTER);

        panel = new VBox(10, buttonBox);
        panel.setPadding(new Insets(10));

        setInitialState();
    }

    public VBox getPanel() {
        return panel;
    }

    public void setOnStart(Runnable action) {
        startButton.setOnAction(e -> {
            action.run();
            setRunningState();
        });
    }

    public void setOnPause(Runnable action) {
        pauseButton.setOnAction(e -> {
            action.run();
            setPausedState();
        });
    }

    public void setOnReset(Runnable action) {
        resetButton.setOnAction(e -> {
            action.run();
            setInitialState();
        });
    }

    public void setOnStop(Runnable action) {
        stopButton.setOnAction(e -> {
            action.run();
            setInitialState();
        });
    }

    public void setInitialState() {
        startButton.setDisable(false);
        pauseButton.setDisable(true);
        resetButton.setDisable(true);
        stopButton.setDisable(true);
    }

    public void setRunningState() {
        startButton.setDisable(true);
        pauseButton.setDisable(false);
        resetButton.setDisable(false);
        stopButton.setDisable(false);
    }

    public void setPausedState() {
        startButton.setDisable(false);
        pauseButton.setDisable(true);
        resetButton.setDisable(false);
        stopButton.setDisable(false);
    }
}
