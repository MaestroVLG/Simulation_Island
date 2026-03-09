package javarush_simulation.model;


import lombok.Getter;

public class Island {
    @Getter
    private final int width;
    @Getter
    private final int height;

    private final Location[][] locations;

    public Island(int WIDTH, int HIGHT) {
        this.width = WIDTH;
        this.height = HIGHT;
        this.locations = new Location[WIDTH][HIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HIGHT; j++) {
                locations[i][j] = new Location();
            }

        }

    }

    public Location getLocation(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("Координаты не найдены");
        }
        return locations[x][y];

    }
}
