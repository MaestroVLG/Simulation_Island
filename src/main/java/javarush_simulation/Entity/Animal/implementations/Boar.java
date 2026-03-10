package javarush_simulation.Entity.Animal.implementations;

import javarush_simulation.Entity.Animal.Herbivore;

public class Boar extends Herbivore {
    private static final double WEIGHT = 400;
    private static final double MAX_SATIETY = 50;
    private static final int SPEED = 2;
    private static final int MAX_PER_CELL = 50;

    public Boar() {
        super("Кабан", WEIGHT, MAX_SATIETY, SPEED, MAX_PER_CELL);
    }

    @Override
    public String toString() {
        return "Кабан";
    }
}