package javarush_simulation.Entity.Animal.implementations;

import javarush_simulation.Entity.Animal.Predator;
import java.util.Map;

public class Python extends Predator {
    private static final double WEIGHT = 15;
    private static final double MAX_SATIETY = 3;
    private static final int SPEED = 1;
    private static final int MAX_PER_CELL = 30;

    public Python() {
        super("Удав", WEIGHT, MAX_SATIETY, SPEED, MAX_PER_CELL, Map.of(
                Rabbit.class, 20,
                Mouse.class, 40,
                Duck.class, 10
        ));
    }

    @Override
    public String toString() {
        return "Удав";
    }
}