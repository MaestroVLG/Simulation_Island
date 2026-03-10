package javarush_simulation.Entity.Animal.implementations;

import javarush_simulation.Entity.Animal.Predator;
import java.util.Map;

public class Wolf extends Predator {
    private static final double WEIGHT = 50;
    private static final double MAX_SATIETY = 8;
    private static final int SPEED = 3;
    private static final int MAX_PER_CELL = 30;

    public Wolf() {
        super("Волк", WEIGHT, MAX_SATIETY, SPEED, MAX_PER_CELL, Map.of(
                Rabbit.class, 60,
                Mouse.class, 80,
                Horse.class, 10,
                Deer.class, 15,
                Goat.class, 60,
                Sheep.class, 70,
                Boar.class, 15,
                Buffalo.class, 10,
                Duck.class, 40
        ));
    }

    @Override
    public String toString() {
        return "Волк";
    }
}