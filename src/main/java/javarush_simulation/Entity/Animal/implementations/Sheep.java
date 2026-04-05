package javarush_simulation.Entity.Animal.implementations;

import javarush_simulation.Entity.Animal.Herbivore;

public class Sheep extends Herbivore {
    private static final double WEIGHT = 70;
    private static final double MAX_SATIETY = 15;
    private static final int SPEED = 3;
    private static final int MAX_PER_CELL = 140;

    public Sheep() {
        super("Овца", WEIGHT, MAX_SATIETY, SPEED, MAX_PER_CELL);
    }

    @Override
    public String toString() {
        return "Овца";
    }
}