package com.traffic.model;

import com.traffic.utils.Constants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

import java.util.function.Consumer;
public class TrafficLight {
    private final Direction direction;
    private LightColor color = LightColor.RED;
    private Timeline cycleTimer;
    private int remainingTime;

    public TrafficLight(Direction direction) {
        this.direction = direction;
    }

    public void startCycle(int greenDuration) {
        cycleTimer = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> {
                    setColor(LightColor.GREEN);
                    setRemainingTime(greenDuration);
                }),
                new KeyFrame(Duration.seconds(greenDuration), e -> {
                    setColor(LightColor.YELLOW);
                    setRemainingTime(Constants.YELLOW_DURATION);
                }),
                new KeyFrame(Duration.seconds(greenDuration + Constants.YELLOW_DURATION), e -> {
                    setColor(LightColor.RED);
                    setRemainingTime(0);
                })
        );
        cycleTimer.setCycleCount(Timeline.INDEFINITE);
        cycleTimer.play();
    }

    public void stopCycle() {
        if (cycleTimer != null) cycleTimer.stop();
    }

    // ✔️ Gerekli getter/setter'lar
    public void setColor(LightColor newColor) {
        this.color = newColor;
    }

    public void setRemainingTime(int time) {
        this.remainingTime = time;
    }

    public void tick() {
        if (remainingTime > 0) remainingTime--;
    }

    public boolean isTimeUp() {
        return remainingTime <= 0;
    }

    public Direction getDirection() { return direction; }
    public LightColor getColor() { return color; }
    public int getRemainingTime() { return remainingTime; }
}
