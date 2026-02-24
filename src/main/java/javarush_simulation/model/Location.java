package javarush_simulation.model;

import javarush_simulation.Entity.Animal.Animal;
import javarush_simulation.Entity.Plant;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Location {

    @Getter

    private final List<Animal> animals = new CopyOnWriteArrayList<>();
    @Getter
    private final List<Plant> plants = new CopyOnWriteArrayList<>();

    public void addAnimal(Animal animal){
        animals.add(animal);
        animal.setCurrentLocation(this);

    }

    public void removeAnimal(Animal animal){
        animals.remove(animal);

    }

    public void addPlant(Plant plant){
        plants.add(plant);

    }

    public Plant removePlant(){
        synchronized (plants){
            if (!plants.isEmpty()){
                return plants.remove(plants.size() -1);

            }
            return null;
        }
    }

}
