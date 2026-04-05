package javarush_simulation.Entity.Animal.implementations;

import javarush_simulation.Entity.Animal.Predator;
import java.util.Map;

public class Fox extends Predator {
    private static final double WEIGHT = 8;
    private static final double MAX_SATIETY = 2;
    private static final int SPEED = 2;
    private static final int MAX_PER_CELL = 30;

    public Fox() {
        super("Лиса", WEIGHT, MAX_SATIETY, SPEED, MAX_PER_CELL, Map.of(
                Rabbit.class, 70,
                Mouse.class, 90,
                Duck.class, 60,
                Caterpillar.class, 40
        ));
    }

    @Override
    public String toString() {
        return "Лиса";
    }
}