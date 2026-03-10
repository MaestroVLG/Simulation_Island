package javarush_simulation;

import javarush_simulation.config.SimulationConfig;
import javarush_simulation.simulation.MultithreadSimulation;

/**
 * Главный класс для запуска симуляции экосистемы.
 * Настраивает конфигурацию, инициализирует и запускает симуляцию.
 */
public class App {
    public static void main(String[] args) {
        // Создаём конфигурацию симуляции
        SimulationConfig config = SimulationConfig.builder()
                .islandWidth(10)
                .islandHeight(10)
                .initialWolves(5)
                .initialPythons(3)
                .initialFoxes(8)
                .initialBears(2)
                .initialEagles(5)
                .initialHorses(10)
                .initialDeer(12)
                .initialRabbits(40)
                .initialMice(80)
                .initialGoats(15)
                .initialSheep(15)
                .initialBoars(6)
                .initialBuffaloes(3)
                .initialDucks(20)
                .initialCaterpillars(200)
                .initialPlantsPerCell(10)
                .plantsGrowthPerTick(1)
                .tickIntervalMs(800)
                .simulationTicks(50)
                .build();

        // Создаём и запускаем симуляцию
        MultithreadSimulation simulation = new MultithreadSimulation(config);
        simulation.initialize();
        simulation.start();

        // Ждём завершения симуляции
        try {
            Thread.sleep(config.getSimulationTicks() * config.getTickIntervalMs() + 2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Основной поток был прерван.");
        }

        System.out.println("✅ Симуляция завершена. Спасибо за наблюдение!");
    }
}