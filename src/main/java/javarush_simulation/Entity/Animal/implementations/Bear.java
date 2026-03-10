package javarush_simulation.Entity.Animal.implementations;

import javarush_simulation.Entity.Animal.Predator;
import java.util.Map;

public class Bear extends Predator {
    public Bear() {
        super("Медведь", 500, 80, 2, 5, Map.of(
                Rabbit.class, 90,
                Mouse.class, 95,
                Horse.class, 25,
                Deer.class, 30,
                Goat.class, 70,
                Sheep.class, 70,
                Boar.class, 60,
                Buffalo.class, 10,
                Duck.class, 20,
                Python.class, 85
        ));
    }
}