package javarush_simulation.Entity.Animal.implementations;

import javarush_simulation.Entity.Animal.Herbivore;
import javarush_simulation.Entity.Plant;
import javarush_simulation.model.Location;
import java.util.concurrent.ThreadLocalRandom;

public class Duck extends Herbivore {
    private static final double WEIGHT = 1;
    private static final double MAX_SATIETY = 0.15;
    private static final int SPEED = 4;
    private static final int MAX_PER_CELL = 200;

    public Duck() {
        super("Утка", WEIGHT, MAX_SATIETY, SPEED, MAX_PER_CELL);
    }

    @Override
    public void eat(Location location) {
        if (!isAlive) return;

        // Сначала пробует съесть гусеницу
        for (var animal : location.getAnimals()) {
            if (animal instanceof Caterpillar && animal.isAlive()
                    && ThreadLocalRandom.current().nextInt(100) < 90) {
                animal.die();
                currentSatiety = Math.min(maxSatiety, currentSatiety + animal.getWeight());
                return;
            }
        }

        // Если гусеницы нет — ест растение
        Plant plant = location.removePlant();
        if (plant != null) {
            currentSatiety = Math.min(maxSatiety, currentSatiety + plant.getWeight());
        }
    }

    @Override
    public String toString() {
        return "Утка";
    }
}