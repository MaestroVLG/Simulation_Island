package javarush_simulation.model;

import javarush_simulation.Entity.Animal.Animal;
import javarush_simulation.Entity.Plant;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Локация (клетка острова), содержащая животных и растения.
 * Потокобезопасна: использует CopyOnWriteArrayList для животных и synchronized блоки для растений.
 */
public class Location {
    private final List<Animal> animals = new CopyOnWriteArrayList<>();
    private final List<Plant> plants = Collections.synchronizedList(new ArrayList<>());
    private final Object plantLock = new Object();

    /**
     * Добавляет животное в локацию, если не превышен лимит.
     */
    public void addAnimal(Animal animal) {
        if (animals.size() < animal.getMaxPerCell()) {
            animals.add(animal);
            animal.setCurrentLocation(this);
        }
    }

    /**
     * Удаляет животное из локации.
     */
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    /**
     * Добавляет растение, если не превышен лимит (200).
     */
    public void addPlant(Plant plant) {
        synchronized (plantLock) {
            if (plants.size() < 200) {
                plants.add(plant);
            }
        }
    }

    /**
     * Удаляет одно растение (если есть).
     * @return удалённое растение или null
     */
    public Plant removePlant() {
        synchronized (plantLock) {
            return plants.isEmpty() ? null : plants.remove(0);
        }
    }

    /**
     * Возвращает копию списка животных для безопасной итерации.
     */
    public List<Animal> getAnimals() {
        return new ArrayList<>(animals);
    }

    /**
     * Возвращает копию списка растений.
     */
    public List<Plant> getPlants() {
        synchronized (plantLock) {
            return new ArrayList<>(plants);
        }
    }

    /**
     * Получить количество растений.
     */
    public int getPlantCount() {
        synchronized (plantLock) {
            return plants.size();
        }
    }
}