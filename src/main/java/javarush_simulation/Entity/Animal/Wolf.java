package javarush_simulation.Entity.Animal;

import javarush_simulation.model.Island;
import javarush_simulation.model.Location;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class Wolf extends Animal{

    private static double WEIGHT = 50;
    private static double MAX_SATIETY = 30;

    private static final Map<Class<? extends Animal>, Integer> EATING_PROBABILITIES = Map.of(Rabbit.class, 60, Deer.class, 80);


    public Wolf() {
        super(WEIGHT, MAX_SATIETY);
        this.eatingProbabilities = EATING_PROBABILITIES;

    }


    @Override
    public void eat(Location location) {
        if(!isAlive){
            return;
        }

        for (Animal prey : location.getAnimals()){
            if(prey == this || !prey.isAlive()) continue;
            Integer prob = eatingProbabilities.get(prey.getClass());
            if (prob != null && ThreadLocalRandom.current().nextInt(100)< prob){
                location.removeAnimal(prey);
                prey.die();
                currentSatiety = Math.min(maxSatiety, currentSatiety + prey.getWeight());
                log.debug("Волк съел {}", prey.getClass().getSimpleName());
                break;
            }
        }

    }



    public void reproduce(Location location){
        //todo пока заглушка
    }

}
