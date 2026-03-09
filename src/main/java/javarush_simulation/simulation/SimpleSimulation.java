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

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс для создания простой однопоточной симуляции
 */
@Slf4j
public class SimpleSimulation {
    private final Island island;
    private final SimulationConfig config;
    private static final double SATIETY_PER_TICK = 0.01; //

    public SimpleSimulation(SimulationConfig config) {
        this.config = config;
        this.island = new Island(config.getIslandWidth(), config.getIslandHeight()); //
    }

    public void initialize() {
        // Размещение волков
        for (int i = 0; i < config.getInitialWolves(); i++) {
            int x = ThreadLocalRandom.current().nextInt(config.getIslandWidth());
            int y = ThreadLocalRandom.current().nextInt(config.getIslandHeight());
            Wolf wolf = new Wolf();
            island.getLocation(x, y).addAnimal(wolf);
        }

        // Размещение кроликов
        for (int i = 0; i < config.getInitialRabbits(); i++) {
            int x = ThreadLocalRandom.current().nextInt(config.getIslandWidth());
            int y = ThreadLocalRandom.current().nextInt(config.getIslandHeight());
            Rabbit rabbit = new Rabbit();
            island.getLocation(x, y).addAnimal(rabbit);
        }

        // Размещение оленей
        for (int i = 0; i < config.getInitialDeer(); i++) {
            int x = ThreadLocalRandom.current().nextInt(config.getIslandWidth());
            int y = ThreadLocalRandom.current().nextInt(config.getIslandHeight());
            Deer deer = new Deer();
            island.getLocation(x, y).addAnimal(deer);
        }

        // Размещение растений (по 5 шт. в каждой клетке)
        for (int y = 0; y < config.getIslandHeight(); y++) {
            for (int x = 0; x < config.getIslandWidth(); x++) {
                Location location = island.getLocation(x, y);
                for (int p = 0; p < 5; p++) {
                    location.addPlant(new Plant());
                }
            }
        }
        log.info("Инициализация завершена. Животные и растения размещены.");
    }

    public void tick() {
        // 1) Рост растений
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x, y);
                for (int i = 0; i < config.getPlantsPerCell(); i++) {
                    location.addPlant(new Plant());
                }
            }
        }

        // 2) Обработка животных
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x, y);
                List<Animal> animals = List.copyOf(location.getAnimals());
                for (Animal animal : animals) {
                    if (!animal.isAlive()) continue;

                    animal.eat(location);
                    animal.move(island, x, y);
                    animal.reproduce(location);

                    animal.setCurrentSatiety(animal.getCurrentSatiety() - SATIETY_PER_TICK);
                    if (animal.getCurrentSatiety() <= 0) {
                        animal.die();
                        location.removeAnimal(animal);
                    }
                }
            }
        }

        printStatistics();
    }

    public void printStatistics() {
        int wolves = 0;
        int rabbits = 0;
        int deer = 0;
        int plants = 0;

        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x, y);
                for (Animal animal : location.getAnimals()) {
                    if (animal instanceof Wolf) wolves++;
                    if (animal instanceof Rabbit) rabbits++;
                    if (animal instanceof Deer) deer++;
                }
                plants += location.getPlants().size(); // считаем растения один раз на локацию
            }
        }

        log.info("Статистика: Волки={}, Кролики={}, Олени={}, Растения={}", wolves, rabbits, deer, plants);
    }

    /**
     * Запуск симуляции на заданное количество тактов
     *
     * @param ticks - количество тактов
     * @throws InterruptedException - если поток был прерван
     */
    public void run(int ticks) throws InterruptedException {
        for (int i = 0; i < ticks; i++) {
            log.info("Такт {}", i + 1);
            tick();
            Thread.sleep(config.getTickDelayMs());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SimulationConfig config = SimulationConfig.builder()
                .islandWidth(5)
                .islandHeight(5)
                .initialWolves(2)
                .initialRabbits(10)
                .initialDeer(5)
                .plantsPerCell(1)
                .tickDelayMs(1000) // добавлено
                .build();

        SimpleSimulation simulation = new SimpleSimulation(config);
        simulation.initialize();
        simulation.run(10);
    }
}