package com.traffic.model;

import com.traffic.utils.Constants;
import javafx.geometry.Point2D;

import java.util.EnumMap;
import java.util.Map;

/**
 * Her yön için ortalanmış bir giriş noktası döner.
 * Genişlik farkı ve yönlenme hesaba katılır.
 */
public class SpawnPoints {
    private static final Map<Direction, Point2D> spawnMap = new EnumMap<>(Direction.class);

    static {
        // Araçlar sola veya sağdan yaklaşacağı için center'dan biraz kaydırılmış giriş noktaları
        spawnMap.put(Direction.NORTH, new Point2D(Constants.CENTER_X - 10, Constants.SIMULATION_HEIGHT + 40));
        spawnMap.put(Direction.SOUTH, new Point2D(Constants.CENTER_X + 10, -40));
        spawnMap.put(Direction.EAST,  new Point2D(-40, Constants.CENTER_Y - 10));
        spawnMap.put(Direction.WEST,  new Point2D(Constants.SIMULATION_WIDTH + 40, Constants.CENTER_Y + 10));
    }

    public static Point2D get(Direction dir) {
        return spawnMap.get(dir);
    }
}
