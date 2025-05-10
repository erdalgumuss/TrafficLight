package com.traffic;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.traffic.view.MainView;
import com.traffic.util.Constants;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainView mainView = new MainView();
        Scene scene = new Scene(mainView.getRoot(), Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        scene.getStylesheets().add(getClass().getResource("/assets/styles.css").toExternalForm());

        primaryStage.setTitle("Akıllı Trafik Işığı Simülasyonu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
