package javarush_simulation.Entity.Animal.implementations;

import javarush_simulation.Entity.Animal.Herbivore;

public class Rabbit extends Herbivore {
    private static final double WEIGHT = 2;
    private static final double MAX_SATIETY = 0.45;
    private static final int SPEED = 2;
    private static final int MAX_PER_CELL = 150;

    public Rabbit() {
        super("Кролик", WEIGHT, MAX_SATIETY, SPEED, MAX_PER_CELL);
    }

    @Override
    public String toString() {
        return "Кролик";
    }
}