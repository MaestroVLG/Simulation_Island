package javarush_simulation.model;

import javarush_simulation.Entity.Animal.Animal;
import javarush_simulation.Entity.Plant;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Location {

    @Getter
    private final List<Animal> animals = new CopyOnWriteArrayList<>(); // можно оставить — add/remove через итератор безопасны

    // Заменяем CopyOnWriteArrayList на synchronizedList
    private final List<Plant> plants = Collections.synchronizedList(new ArrayList<>());

    public void addAnimal(Animal animal) {
        animals.add(animal);
        animal.setCurrentLocation(this);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }


    public Plant removePlant() {
        synchronized (plants) { // обязательно синхронизируем блок!
            if (!plants.isEmpty()) {
                return plants.remove(plants.size() - 1); // теперь работает
            }
            return null;
        }
    }

    /**
     * Возвращает НЕИЗМЕНЯЕМУЮ копию списка растений
     * Чтобы избежать ConcurrentModificationException при итерации
     */
    public List<Plant> getPlants() {
        synchronized (plants) {
            return new ArrayList<>(plants); // безопасная копия
        }
    }
}