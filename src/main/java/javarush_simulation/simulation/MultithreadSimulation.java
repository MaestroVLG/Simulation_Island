package javarush_simulation.simulation;

import javarush_simulation.Entity.Animal.Animal;
import javarush_simulation.Entity.Animal.Deer;
import javarush_simulation.Entity.Animal.Rabbit;
import javarush_simulation.Entity.Animal.Wolf;
import javarush_simulation.Entity.Plant;
import javarush_simulation.config.SimulationConfig;
import javarush_simulation.model.Island;
import javarush_simulation.model.Location;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class MultithreadSimulation {
    private static final int CORE_PULL_SIZE = 1;
    private static final int THREADS = 10;
    private final Island island;
    private final SimulationConfig config;
    private final ExecutorService workerPool = Executors.newFixedThreadPool(THREADS);
    private volatile boolean running = true;


    public MultithreadSimulation(SimulationConfig config) {
        this.island = new Island(config.getIslandWidth(), config.getIslandHeigth());
        this.config = config;

    }

    public void initialize() {

        // Волки
        for (int i = 0; i < config.getInnitiaslisationWolves(); i++) {
            int x = ThreadLocalRandom.current().nextInt(config.getIslandWidth());
            int y = ThreadLocalRandom.current().nextInt(config.getIslandHeigth());
            Wolf wolf = new Wolf();
            island.getLocation(x, y).addAnimal(wolf);


        }

        // Кролики
        for (int i = 0; i < config.getInnitiaslisationRabbits(); i++) {
            int x = ThreadLocalRandom.current().nextInt(config.getIslandWidth());
            int y = ThreadLocalRandom.current().nextInt(config.getIslandHeigth());
            Rabbit rabbit = new Rabbit();
            island.getLocation(x, y).addAnimal(rabbit);
        }

        // Олени
        for (int i = 0; i < config.getInnitiaslisationDeer(); i++) {
            int x = ThreadLocalRandom.current().nextInt(config.getIslandWidth());
            int y = ThreadLocalRandom.current().nextInt(config.getIslandHeigth());
            Deer deer = new Deer();
            island.getLocation(x, y).addAnimal(deer);


        }

        //Растения

        for (int y = 0; y < island.getHeight; y++) {
            for (int x = 0; x < island.getWeight; x++) {
                Location location = island.getLocation(x, y);
                for (int p = 0; p < 5; p++) {
                    location.addPlant(new Plant());
                }

            }
        }
        log.info("Инициализация завершена. Животные и растения размещены");
    }

    private void tick() {
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x, y);
                for (int i = 0; i < config.getPlantsPerCell(); i++) {
                    location.addPlant(new Plant());
                }
            }
        }

        List<Callable<Void>> tasks = new ArrayList<>();
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x, y);
                int finalX = x;
                int finalY = y;
                for (Animal animal : location.getAnimals()) {
                    if (!animal.isAlive()) {
                        continue;
                    }

                    tasks.add(() -> {
                        animal.eat(animal.getCurrentLocation());
                        animal.move(island, finalX, finalY);
                        animal.reproduce(animal.getCurrentLocation());
                        animal.setCurrentSatiety(animal.getCurrentSatiety() - 1);
                        if (animal.getCurrentSatiety() == 0) {
                            animal.die();
                            animal.getCurrentLocation().removeAnimal(animal);
                        }
                        return null;


                    });

                }

            }

        }

        try {
            List<Future<Void>> futures = workerPool.invokeAll(tasks); //отправляем действия в пул рабочих потоков
            for (Future<Void> f : futures) {
                f.get();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Такт прерван");
        } catch (ExecutionException e) {
            log.error("Ошибка при выполнении задачи животного", e.getCause());
        }
        printStatistics();

    }

    public void printStatistics() {
        int wolves = 0;
        int rabbits = 0;
        int deer = 0;
        int plants = 0;
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) ;
            Location location = island.getLocation(x, y);
            for (Animal animal : location.getAnimals()) {
                if (animal instanceof Wolf) wolves++;
                else if (animal instanceof Rabbit) rabbits++;
                else if (animal instanceof Deer) deer++;
            }
            plants += location.getPlants().size();
        }

    }
    log.info("Статистика: Волки = {}, Кролики = {}, Олени = {}, Растения = {}",wolves,rabbits,deer,plants);

}

public void start() {
    sheduler.sheduleAtFixedRate(() -> {
        if (running) {
            tick;
        }
    }, 0, config.getTickDurationMs(), TimeUnit.MICROSECONDS);
    log.info("Симуляция запущена с тактом {} мс", config.getTickDurationMs());
}

public void stop() {
    running = false;
    sheduler.shutdown();
    workerPool.shutdown();
    log.info("Симуляция остановлена!");
}
}


