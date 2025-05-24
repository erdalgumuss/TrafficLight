package com.traffic.utils;

public class Constants {

    // === EKRAN VE PANEL BOYUTLARI ===
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 700;

    public static final int SIMULATION_WIDTH = 640;
    public static final int SIMULATION_HEIGHT = 480;

    // === MERKEZ KOORDİNATLAR (Kavşağın tam ortası) ===
    public static final double CENTER_X = SIMULATION_WIDTH / 2.0;  // 320.0
    public static final double CENTER_Y = SIMULATION_HEIGHT / 2.0; // 240.0

    // === KAVŞAK BOYUTU & GÜVENLİK ZONLARI ===
    public static final double INTERSECTION_SIZE = 80.0; // genişlik/yükseklik
    public static final double INTERSECTION_HALF = INTERSECTION_SIZE / 2.0;

    // === STOP LINE KONUMU (ışık olmasa da kontrol noktası) ===
    public static final double STOP_LINE_NORTH_Y = CENTER_Y - INTERSECTION_HALF - 10; // 190.0
    public static final double STOP_LINE_SOUTH_Y = CENTER_Y + INTERSECTION_HALF + 10; // 290.0
    public static final double STOP_LINE_WEST_X  = CENTER_X - INTERSECTION_HALF - 10; // 230.0
    public static final double STOP_LINE_EAST_X  = CENTER_X + INTERSECTION_HALF + 10; // 410.0

    // === TAKİP MESAFESİ ve GÖRSEL HAREKET ===
    public static final double SAFE_DISTANCE = 40.0; // Daha fazla dikkat gerektiren durum
    public static final int VEHICLE_SPAWN_RATE = 2;  // her 2 saniyede bir deneme

    // === IŞIK KULLANILACAKSA (opsiyonel sistemlerde null geçilebilir) ===
    public static final int YELLOW_DURATION      = 3;   // sn
    public static final int MIN_GREEN_DURATION   = 10;  // sn
    public static final int MAX_GREEN_DURATION   = 60;  // sn
    public static final int TOTAL_CYCLE_TIME     = 120; // sn

    // === GÖRSELLER (opsiyonel – asset paths) ===
    public static final String[] VEHICLE_IMAGES = {
            "/assets/icons/vehicles/car1.png",
            "/assets/icons/vehicles/car2.png",
            "/assets/icons/vehicles/truck.png",
            "/assets/icons/vehicles/van.png"
    };

    private Constants() {
        // Sabit sınıfı – instance oluşturulamaz
    }
}
