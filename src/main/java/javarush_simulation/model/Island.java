package javarush_simulation.model;


public class Island {

    private final int WIDTH;
    private final int HIGHT;

    private final Location[][] locations;

    public Island(int WIDTH, int HIGHT) {
        this.WIDTH = WIDTH;
        this.HIGHT = HIGHT;
        this.locations = new Location[WIDTH][HIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HIGHT; j++) {
                locations[i][j] = new Location();
            }

        }

    }

    public Location getLocation(int x, int y) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HIGHT) {
            throw new IllegalArgumentException("Координаты не найдены");
        }
        return locations[x][y];

    }
}
