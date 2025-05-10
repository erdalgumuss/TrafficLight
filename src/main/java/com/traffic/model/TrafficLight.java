package com.traffic.model;

import com.traffic.util.Constants;

public class TrafficLight {
    private final Direction direction;
    private LightColor color;
    private int remainingTime;
    private int elapsedTime;

    public TrafficLight(Direction direction) {
        this.direction = direction;
        this.color = LightColor.RED;
        this.remainingTime = 0;
        this.elapsedTime = 0;
    }

    public Direction getDirection() {
        return direction;
    }

    public LightColor getColor() {
        return color;
    }

    public void setColor(LightColor color) {
        this.color = color;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
        this.elapsedTime = 0;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void tick() {
        if (remainingTime > 0) {
            remainingTime--;
            elapsedTime++;
        }
    }

    public boolean isTimeUp() {
        return remainingTime <= 0;
    }

    /**
     * Renk geçişi yapar ve sarı faz için süreyi ayarlar.
     * Yeşilden sarıya geçişte Constants.YELLOW_DURATION kullanılır.
     * Sarıdan kırmızıya geçişte süre sıfırlanır;
     * kırmızıdan yeşile geçişte sürenin dışarıdan setlenmesi beklenir.
     */
    public void nextState() {
        switch (color) {
            case GREEN -> {
                color = LightColor.YELLOW;
                setRemainingTime(Constants.YELLOW_DURATION);
            }
            case YELLOW -> {
                color = LightColor.RED;
                setRemainingTime(0);
            }
            case RED -> {
                color = LightColor.GREEN;
                // Yeşil süresi engine tarafından setRemainingTime ile atanmalıdır
            }
        }
    }

    public boolean isRed() {
        return color == LightColor.RED;
    }

    public boolean isYellow() {
        return color == LightColor.YELLOW;
    }

    public boolean isGreen() {
        return color == LightColor.GREEN;
    }
}
