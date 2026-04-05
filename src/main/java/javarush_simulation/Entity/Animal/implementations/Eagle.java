package javarush_simulation.Entity.Animal.implementations;

import javarush_simulation.Entity.Animal.Predator;
import java.util.Map;

public class Eagle extends Predator {
    private static final double WEIGHT = 6;
    private static final double MAX_SATIETY = 1;
    private static final int SPEED = 3;
    private static final int MAX_PER_CELL = 20;

    public Eagle() {
        super("Орёл", WEIGHT, MAX_SATIETY, SPEED, MAX_PER_CELL, Map.of(
                Rabbit.class, 90,
                Mouse.class, 90,
                Duck.class, 80,
                Fox.class, 10
        ));
    }

    @Override
    public String toString() {
        return "Орёл";
    }
}