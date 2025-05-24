package com.traffic.view;

import com.traffic.controller.SimulationManager;
import com.traffic.model.Direction;
import com.traffic.utils.Constants;
import com.traffic.view.controls.SimulationControlPanel;
import com.traffic.view.input.TrafficInputPanel;
import com.traffic.view.status.StatsPanel;
import com.traffic.view.status.TimeIndicatorPanel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Map;

public class MainView {
    private final BorderPane root;
    private final TrafficInputPanel inputPanel;
    private final TimeIndicatorPanel timePanel;
    private final SimulationControlPanel controlPanel;
    private final StatsPanel statsPanel;

    private final Pane simulationPane;
    private final ImageView background;

    private VehicleAnimator vehicleAnimator;
    private SimulationManager simulationManager;
    private Timeline statsUpdater;

    public MainView() {
        root = new BorderPane();
        inputPanel = new TrafficInputPanel();
        timePanel = new TimeIndicatorPanel();
        controlPanel = new SimulationControlPanel();
        statsPanel = new StatsPanel();

        // Stil sınıfları ekleniyor
        inputPanel.getPanel().getStyleClass().add("panel");
        timePanel.getPanel().getStyleClass().add("panel");
        controlPanel.getPanel().getStyleClass().add("panel");
        statsPanel.getPanel().getStyleClass().add("panel");

        simulationPane = new Pane();
        simulationPane.setPrefSize(Constants.SIMULATION_WIDTH, Constants.SIMULATION_HEIGHT);

        background = new ImageView(
                new Image(getClass().getResource("/assets/map/intersection.png").toExternalForm())
        );
        background.setFitWidth(Constants.SIMULATION_WIDTH);
        background.setFitHeight(Constants.SIMULATION_HEIGHT);
        background.setPreserveRatio(false);
        simulationPane.getChildren().add(background);

        setupUI();
        setupActions();
    }

    private void setupUI() {
        Text title = new Text("Akıllı Trafik Işığı Simülasyonu");
        title.setFont(Font.font("Arial", 20));
        title.getStyleClass().add("title-label");

        VBox leftPanel = new VBox(20,
                inputPanel.getPanel(),
                timePanel.getPanel(),
                controlPanel.getPanel(),
                statsPanel.getPanel()
        );
        leftPanel.setPadding(new Insets(20));

        HBox mainContent = new HBox(20, leftPanel, simulationPane);
        mainContent.setPadding(new Insets(20));

        VBox layout = new VBox(20, title, mainContent);
        layout.setPadding(new Insets(20));

        root.setCenter(layout);
    }

    private void setupActions() {
        controlPanel.setOnStart(this::startSimulation);

        controlPanel.setOnPause(() -> {
            if (simulationManager != null) simulationManager.pause();
            if (statsUpdater != null) statsUpdater.pause();
        });

        controlPanel.setOnReset(() -> {
            if (simulationManager != null) simulationManager.reset();
            if (statsUpdater != null) statsUpdater.stop();
            simulationPane.getChildren().clear();
            simulationPane.getChildren().add(background);
            timePanel.clear();
            statsPanel.clear();
        });
    }

    private void startSimulation() {
        Map<Direction, Integer> densityMap = inputPanel.getDensityValues();

        if (densityMap == null || densityMap.values().stream().allMatch(v -> v == 0)) {
            System.out.println("Lütfen en az bir yön için araç sayısı giriniz.");
            return;
        }

        vehicleAnimator = new VehicleAnimator(simulationPane);

        simulationManager = new SimulationManager(
                vehicleAnimator,
                densityMap,
                (dir, color, time) ->
                        timePanel.updateTime(dir, dir.name() + " → " + color + " (" + time + " sn)")
        );

        simulationManager.start();

        statsUpdater = new Timeline(new KeyFrame(Duration.seconds(1), e ->
                statsPanel.update(simulationManager.getStats())
        ));
        statsUpdater.setCycleCount(Timeline.INDEFINITE);
        statsUpdater.play();
    }

    public BorderPane getRoot() {
        return root;
    }
}
