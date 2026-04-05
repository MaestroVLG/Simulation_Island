package javarush_simulation.Entity.Animal.implementations;

import javarush_simulation.Entity.Animal.Herbivore;

public class Mouse extends Herbivore {
    private static final double WEIGHT = 0.05;
    private static final double MAX_SATIETY = 0.01;
    private static final int SPEED = 1;
    private static final int MAX_PER_CELL = 500;

    public Mouse() {
        super("Мышь", WEIGHT, MAX_SATIETY, SPEED, MAX_PER_CELL);
    }

    @Override
    public String toString() {
        return "Мышь";
    }
}