package javarush_simulation.Entity.Animal;

import javarush_simulation.Entity.Plant;
import javarush_simulation.model.Island;
import javarush_simulation.model.Location;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Deer extends Animal{

    private static final double WEIGHT = 170;
    private static final double MAX_SATIETY = 50;

    public Deer(){
        super(WEIGHT, MAX_SATIETY);
    }


    @Override
    public void eat(Location location) {
        if(!isAlive){
            return;
        }

        Plant plant = location.removePlant();
        if(plant != null){
            currentSatiety = Math.min(maxSatiety, currentSatiety + plant.getWeight());
            log.debug("Олень съел растение");
        }

    }



    public void reproduce(Location location){
        // todo пока заглушка
    }
}
