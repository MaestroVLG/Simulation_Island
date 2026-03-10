package javarush_simulation.Entity.Animal;

import javarush_simulation.model.Location;
import java.util.Map;

/**
 * Абстрактный класс для хищников.
 * Хищник может охотиться на других животных с определённой вероятностью.
 */
public abstract class Predator extends Animal {
    private final Map<Class<? extends Animal>, Integer> huntingProbabilities;

    public Predator(String name, double weight, double maxSatiety, int speed, int maxPerCell,
                    Map<Class<? extends Animal>, Integer> huntingProbabilities) {
        super(name, weight, maxSatiety, speed, maxPerCell);
        this.huntingProbabilities = huntingProbabilities;
    }

    @Override
    public void eat(Location location) {
        if (!isAlive) return;

        for (Animal prey : location.getAnimals()) {
            if (prey == this || !prey.isAlive()) continue;

            Integer probability = getHuntingProbabilities().get(prey.getClass());
            if (probability != null && Math.random() < probability / 100.0) {
                prey.die();
                currentSatiety = Math.min(maxSatiety, currentSatiety + prey.getWeight());
                break;
            }
        }
    }

    /**
     * Возвращает карту вероятностей охоты на другие виды животных.
     * Используется в подклассах (например, Bear).
     */
    protected Map<Class<? extends Animal>, Integer> getHuntingProbabilities() {
        return huntingProbabilities;
    }
}