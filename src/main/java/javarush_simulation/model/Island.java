package javarush_simulation.model;

import lombok.Getter;

/**
 * Модель острова — двумерный массив локаций.
 */
public class Island {
    @Getter private final int width;
    @Getter private final int height;
    private final Location[][] locations;

    public Island(int width, int height) {
        this.width = width;
        this.height = height;
        this.locations = new Location[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                locations[x][y] = new Location();
            }
        }
    }

    /**
     * Получить локацию по координатам.
     */
    public Location getLocation(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("Координаты за пределами острова: x=" + x + ", y=" + y);
        }
        return locations[x][y];
    }

    /**
     * Найти X-координату по ссылке на локацию.
     */
    public int findX(Location location) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (locations[x][y] == location) {
                    return x;
                }
            }
        }
        return -1;
    }

    /**
     * Найти Y-координату по ссылке на локацию.
     */
    public int findY(Location location) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (locations[x][y] == location) {
                    return y;
                }
            }
        }
        return -1;
    }
}