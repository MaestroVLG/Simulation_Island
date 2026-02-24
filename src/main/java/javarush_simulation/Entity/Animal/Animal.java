package javarush_simulation.Entity.Animal;

import javarush_simulation.model.Island;
import javarush_simulation.model.Location;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadLocalRandom;

@Data
@NoArgsConstructor
@Slf4j

public abstract class Animal {

    private static final CHANCE_OF_REPRODUCTION = 30;
    protected int speed = 1;
    protected double weight;
    protected double currentSatiety;
    protected double maxSatiety;
    protected boolean isAlive = true;
    protected int maxDayLive;
    protected volatile Location currentLocation;


    public Animal(double weight, double maxSatiety) {
        this.weight = weight;
        this.maxSatiety = maxSatiety;
        this.currentSatiety = maxSatiety;

    }


    public abstract void eat(Location location);

    public void reproduce(Location location) {

        if (!isAlive) return;

        long countSpeciesType = location.getAnimals().stream()
                .filter(a -> a.getClass() == this.getClass() && a != this && a.isAlive())
                .count();

        if (countSpeciesType > 0 && ThreadLocalRandom.current().nextInt(100) < CHANCE_OF_REPRODUCTION) {
            try {
                Animal baby = this.getClass().getDeclaredConstructor().newInstance();
                baby.setCurrentSatiety(baby.getMaxSatiety() / 2);
                location.addAnimal(baby);
                log.debug("Родилось новое животное {}", baby.getClass().getSimpleName());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                log.error("Ошибка при создании потомства", e);
                throw new RuntimeException(e);

            }

        }
    }

    public void die() {
        this.isAlive = false;
    }



public void move(Island island, int currentX, int currentY) {
    if (!isAlive) return;
    if (currentLocation == null) {
        log.warn("Животное {} не имеет текущей локации, перемещение не возможно", this);
        return;

    }

    int direction = ThreadLocalRandom.current().nextInt(4);
    int newX = currentX;
    int newY = currentY;

    switch (direction) {
        case 0:
            newY = Math.max(0, currentY - 1);
            break;
        case 1:
            newX = Math.min(island.getWith() - 1, currentY + 1);
            break;
        case 2:
            newY = Math.min(island.getHeight() - 1, currentY + 1);
            break;
        case 3:
            newX = Math.max(0, currentX - 1);
            break;
    }

    if (newX != currentX || newY != currentY) {
        Location newLoc = island.getLocation(newX, newY);
        currentLocation.removeAnimal(this);
        newLoc.addAnimal(this);
    }


}



}
