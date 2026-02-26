package javarush_simulation;


import javarush_simulation.config.SimulationConfig;
import javarush_simulation.simulation.SimpleSimulation;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class App {
    private static final int SIMPLE_SIMULATION_TIKS = 10;

    public static void main(String[] args) {

        SimulationConfig config = SimulationConfig.builder()
                .islandWidth(5) // Размеры Леса (ширина)
                .islandHeigth(5) // Размера Леса (высота)
                .innitiaslisationWolves(2) // Популяция Волков
                .innitiaslisationRabbits(10) // Популяция Кроликов
                .innitiaslisationDeer(5) // Популяция Оленей
                .plantsPerCell(1) // Число растений на 1 ячейку
                .build();


        //Однопоточная симуляция

        SimpleSimulation simpleSimulation = new SimpleSimulation(config);
        simpleSimulation.initialize();

        //Вывод сконфигурированного состояния: err, info, debug;
        log.info("Начальное состояние симуляции: ");
        simpleSimulation.printStatistics();

        try {
            simpleSimulation.run(SIMPLE_SIMULATION_TIKS);
        }catch (InterruptedException e){
            log.error("ошибка при работе simplesimulation");
            throw new RuntimeException(e);
        }

        log.info("Работа симуляции завершена!");



    }
}