package javarush_simulation.Entity.Animal;

import javarush_simulation.model.Island;
import javarush_simulation.model.Location;
import lombok.Getter;
import lombok.Setter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Абстрактный класс для всех животных.
 * Содержит общее поведение: перемещение, размножение, голодание.
 */
@Getter
public abstract class Animal {
    protected final String name;
    protected final double weight;
    protected final double maxSatiety;
    protected final int speed;
    protected final int maxPerCell;

    @Setter
    protected double currentSatiety;
    @Setter
    protected boolean isAlive = true;
    @Setter
    protected Location currentLocation;

    public Animal(String name, double weight, double maxSatiety, int speed, int maxPerCell) {
        this.name = name;
        this.weight = weight;
        this.maxSatiety = maxSatiety;
        this.currentSatiety = maxSatiety;
        this.speed = speed;
        this.maxPerCell = maxPerCell;
    }

    /**
     * Абстрактный метод питания — реализуется в подклассах.
     */
    public abstract void eat(Location location);

    /**
     * Перемещение по острову на 1..speed клеток за ход.
     */
    public void move(Island island, int currentX, int currentY) {
        if (!isAlive || currentLocation == null) return;

        int steps = ThreadLocalRandom.current().nextInt(1, speed + 1);

        int x = currentX;
        int y = currentY;

        for (int i = 0; i < steps; i++) {
            int direction = ThreadLocalRandom.current().nextInt(4);
            int newX = x, newY = y;

            switch (direction) {
                case 0 -> newY = Math.max(0, y - 1); // вверх
                case 1 -> newX = Math.min(island.getWidth() - 1, x + 1); // вправо
                case 2 -> newY = Math.min(island.getHeight() - 1, y + 1); // вниз
                case 3 -> newX = Math.max(0, x - 1); // влево
            }

            if (newX != x || newY != y) {
                Location newLoc = island.getLocation(newX, newY);
                currentLocation.removeAnimal(this);
                newLoc.addAnimal(this);

                x = newX;
                y = newY;
                // Обновляем текущую локацию
                this.currentLocation = newLoc;
            } else {
                break;
            }
        }
    }

    /**
     * Размножение: если есть пара, с шансом 30% рождается потомок.
     */
    public void reproduce(Location location) {
        if (!isAlive) return;

        boolean hasMate = location.getAnimals().stream()
                .anyMatch(animal -> animal.getClass() == this.getClass() && animal != this && animal.isAlive());

        if (hasMate && ThreadLocalRandom.current().nextInt(100) < 30) {
            try {
                Animal baby = this.getClass().getDeclaredConstructor().newInstance();
                baby.setCurrentSatiety(baby.getMaxSatiety() / 2);
                location.addAnimal(baby);
            } catch (Exception e) {
                System.err.println("Не удалось создать потомство для " + this.name);
                e.printStackTrace();
            }
        }
    }

    /**
     * Голодание: сытость уменьшается каждый такт.
     */
    public void starve() {
        currentSatiety -= 0.01;
        if (currentSatiety <= 0) {
            die();
        }
    }

    /**
     * Смерть животного.
     */
    public void die() {
        isAlive = false;
        if (currentLocation != null) {
            currentLocation.removeAnimal(this);
        }
    }
}