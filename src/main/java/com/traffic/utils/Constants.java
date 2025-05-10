package com.traffic.util;

public class Constants {
    // === EKRAN VE PANEL ===
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 700;

    public static final int SIMULATION_WIDTH = 640;
    public static final int SIMULATION_HEIGHT = 480;

    // === MERKEZ KOORDİNATLAR ===
    public static final double CENTER_X = SIMULATION_WIDTH / 2.0;
    public static final double CENTER_Y = SIMULATION_HEIGHT / 2.0;

    // === DURMA ÇİZGİLERİ ===
    public static final double STOP_LINE_NORTH_Y = 220.0;
    public static final double STOP_LINE_SOUTH_Y = 260.0;
    public static final double STOP_LINE_EAST_X  = 420.0;
    public static final double STOP_LINE_WEST_X  = 220.0;

    // === TAKİP MESAFESİ VE HAREKET ===
    public static final double SAFE_DISTANCE = 35.0;      // px
    public static final int VEHICLE_SPAWN_RATE = 2;       // her 2 sn'de bir araç denemesi

    // === IŞIK SÜRELERİ ===
    public static final int YELLOW_DURATION = 3;          // sn
    public static final int MIN_GREEN_DURATION = 10;      // sn
    public static final int MAX_GREEN_DURATION = 60;      // sn
    public static final int TOTAL_CYCLE_TIME = 120;       // toplam simülasyon süresi (sn)

    // === ARAÇ GÖRSELLERİ ===
    public static final String[] VEHICLE_IMAGES = {
            "/assets/icons/vehicles/car1.png",
            "/assets/icons/vehicles/car2.png",
            "/assets/icons/vehicles/truck.png",
            "/assets/icons/vehicles/van.png"
    };

    private Constants() {
        // Bu sınıf sadece sabitler için kullanılmalı
    }
}
