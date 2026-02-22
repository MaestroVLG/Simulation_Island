package javarush_simulation;

import javarush_simulation.Entity.Animal;
import javarush_simulation.Entity.Plant;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Location {

    @Getter

    private final List<Animal> animals = new CopyOnWriteArrayList<>();
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

    public List<Plant> getPlants() {
        return plants;

    }

}
