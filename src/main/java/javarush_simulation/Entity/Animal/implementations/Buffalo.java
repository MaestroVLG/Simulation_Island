package javarush_simulation.Entity.Animal.implementations;

import javarush_simulation.Entity.Animal.Herbivore;

public class Buffalo extends Herbivore {
    private static final double WEIGHT = 700;
    private static final double MAX_SATIETY = 100;
    private static final int SPEED = 3;
    private static final int MAX_PER_CELL = 10;

    public Buffalo() {
        super("Буйвол", WEIGHT, MAX_SATIETY, SPEED, MAX_PER_CELL);
    }

    @Override
    public String toString() {
        return "Буйвол";
    }
}