package javarush_simulation.Entity.Animal.implementations;

import javarush_simulation.Entity.Animal.Herbivore;

public class Horse extends Herbivore {
    private static final double WEIGHT = 400;
    private static final double MAX_SATIETY = 60;
    private static final int SPEED = 4;
    private static final int MAX_PER_CELL = 20;

    public Horse() {
        super("Лошадь", WEIGHT, MAX_SATIETY, SPEED, MAX_PER_CELL);
    }

    @Override
    public String toString() {
        return "Лошадь";
    }
}