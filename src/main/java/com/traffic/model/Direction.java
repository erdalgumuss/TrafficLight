package com.traffic.model;

public enum Direction {
    NORTH("Kuzey"),
    SOUTH("Güney"),
    EAST("Doğu"),
    WEST("Batı");

    private final String displayName;

    Direction(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Türkçe (veya istenen dilde) gösterim.
     */
    @Override
    public String toString() {
        return displayName;
    }

    /**
     * Gerekirse ters eşleme yapılabilir (opsiyonel).
     */
    public static Direction fromString(String value) {
        for (Direction d : Direction.values()) {
            if (d.displayName.equalsIgnoreCase(value)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Geçersiz yön adı: " + value);
    }
}
