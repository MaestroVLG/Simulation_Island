package javarush_simulation.Entity.Animal;

import javarush_simulation.Entity.Plant;
import javarush_simulation.model.Location;

/**
 * Абстрактный класс для травоядных.
 * Травоядное ест растения, если они есть в локации.
 */
public abstract class Herbivore extends Animal {

    public Herbivore(String name, double weight, double maxSatiety, int speed, int maxPerCell) {
        super(name, weight, maxSatiety, speed, maxPerCell);
    }

    @Override
    public void eat(Location location) {
        if (!isAlive) return;

        Plant plant = location.removePlant();
        if (plant != null) {
            currentSatiety = Math.min(maxSatiety, currentSatiety + plant.getWeight());
        }
    }
}