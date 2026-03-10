package javarush_simulation.simulation;

import javarush_simulation.Entity.Animal.Animal;
import javarush_simulation.Entity.Plant;
import javarush_simulation.config.SimulationConfig;
import javarush_simulation.model.Island;
import javarush_simulation.model.Location;
import javarush_simulation.Entity.Animal.factory.AnimalFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;

import static java.util.stream.Collectors.*;

/**
 * Многопоточная симуляция экосистемы.
 * Управляет жизненным циклом животных, ростом растений и статистикой.
 */
@Slf4j
public class MultithreadSimulation {
    private final Island island;
    private final SimulationConfig config;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
    private final ExecutorService workerPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private volatile boolean running = true;

    public MultithreadSimulation(SimulationConfig config) {
        this.config = config;
        this.island = new Island(config.getIslandWidth(), config.getIslandHeight());
    }

    /**
     * Инициализация: размещение животных и растений на острове.
     */
    public void initialize() {
        log.info("Инициализация симуляции... Остров {}x{}", island.getWidth(), island.getHeight());

        // Размещаем всех животных
        for (Class<? extends Animal> animalType : SimulationConfig.getAnimalTypes()) {
            int count = config.getInitialCount(animalType);
            for (int i = 0; i < count; i++) {
                int x = ThreadLocalRandom.current().nextInt(config.getIslandWidth());
                int y = ThreadLocalRandom.current().nextInt(config.getIslandHeight());
                Animal animal = AnimalFactory.create(animalType);
                island.getLocation(x, y).addAnimal(animal);
            }
        }

        // Размещаем растения
        for (int x = 0; x < island.getWidth(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                Location loc = island.getLocation(x, y);
                for (int i = 0; i < config.getInitialPlantsPerCell(); i++) {
                    loc.addPlant(new Plant());
                }
            }
        }

        log.info("Инициализация завершена. Животные и растения размещены.");
        printStatistics();
    }

    /**
     * Запуск симуляции.
     */
    public void start() {
        if (!running) return;

        log.info("Запуск симуляции. Такт каждые {} мс", config.getTickIntervalMs());

        // Задача 1: Рост растений
        scheduler.scheduleAtFixedRate(this::growPlants, 0, config.getTickIntervalMs(), TimeUnit.MILLISECONDS);

        // Задача 2: Жизненный цикл животных
        scheduler.scheduleAtFixedRate(this::processAnimals, 0, config.getTickIntervalMs(), TimeUnit.MILLISECONDS);

        // Задача 3: Статистика
        scheduler.scheduleAtFixedRate(this::printStatistics,
                0, config.getTickIntervalMs() * 2, TimeUnit.MILLISECONDS);

        // Автоматическая остановка
        scheduler.schedule(this::stop, config.getTickIntervalMs() * config.getSimulationTicks(), TimeUnit.MILLISECONDS);
    }

    /**
     * Рост растений на всех локациях.
     */
    private void growPlants() {
        for (int x = 0; x < island.getWidth(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                Location loc = island.getLocation(x, y);
                synchronized (loc) {
                    for (int i = 0; i < config.getPlantsGrowthPerTick(); i++) {
                        if (loc.getPlantCount() < 200) {
                            loc.addPlant(new Plant());
                        }
                    }
                }
            }
        }
    }

    /**
     * Обработка жизненного цикла всех животных (в многопоточном режиме).
     */
    private void processAnimals() {
        List<Callable<Void>> tasks = new ArrayList<>();

        for (int x = 0; x < island.getWidth(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                Location loc = island.getLocation(x, y);
                List<Animal> animals = new ArrayList<>(loc.getAnimals()); // безопасная копия

                for (Animal animal : animals) {
                    int finalX = x;
                    int finalY = y;
                    tasks.add(() -> {
                        if (!animal.isAlive()) return null;

                        animal.eat(loc);
                        animal.reproduce(loc);
                        animal.move(island, finalX, finalY);
                        animal.starve();

                        return null;
                    });
                }
            }
        }

        try {
            workerPool.invokeAll(tasks);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Потоки обработки животных прерваны");
        }
    }

    /**
     * Вывод статистики по популяциям.
     */
    public void printStatistics() {
        Map<String, Long> stats = new HashMap<>();
        int plantCount = 0;

        for (int x = 0; x < island.getWidth(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                Location loc = island.getLocation(x, y);
                plantCount += loc.getPlantCount();

                loc.getAnimals().stream()
                        .filter(Animal::isAlive)
                        .collect(groupingBy(Object::toString, counting()))
                        .forEach((name, count) -> stats.merge(name, count, Long::sum));
            }
        }

        log.info("📊 Статистика: {}", formatStats(stats, plantCount));
    }

    private String formatStats(Map<String, Long> stats, int plantCount) {
        StringBuilder sb = new StringBuilder();
        stats.forEach((k, v) -> sb.append(k).append("=").append(v).append(" "));
        sb.append("Растения=").append(plantCount);
        return sb.toString();
    }

    /**
     * Остановка симуляции.
     */
    public void stop() {
        if (!running) return;
        running = false;

        log.info("Остановка симуляции...");

        scheduler.shutdown();
        workerPool.shutdown();

        try {
            if (!workerPool.awaitTermination(2, TimeUnit.SECONDS)) {
                workerPool.shutdownNow();
            }
            if (!scheduler.awaitTermination(2, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            workerPool.shutdownNow();
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }

        log.info("Симуляция остановлена.");
    }
}