package javarush_simulation.Entity.Animal.implementations;

import javarush_simulation.Entity.Animal.Herbivore;

public class Caterpillar extends Herbivore {
    private static final double WEIGHT = 0.01;
    private static final double MAX_SATIETY = 0.01;
    private static final int SPEED = 0;
    private static final int MAX_PER_CELL = 1000;

    public Caterpillar() {
        super("Гусеница", WEIGHT, MAX_SATIETY, SPEED, MAX_PER_CELL);
    }

    @Override
    public void move(javarush_simulation.model.Island island, int x, int y) {
        // Гусеница не двигается
    }

    @Override
    public String toString() {
        return "Гусеница";
    }
}