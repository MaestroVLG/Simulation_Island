package javarush_simulation.config;

import javarush_simulation.Entity.Animal.implementations.*;
import lombok.Builder;
import lombok.Getter;



/**
 * Конфигурация симуляции.
 * Все параметры задаются через билдер.
 */


@Getter
@Builder
public class SimulationConfig {
    //  Размеры острова
    private final int islandWidth;
    private final int islandHeight;

    // Хищники
    @Builder.Default
    private final int initialWolves = 10;
    @Builder.Default
    private final int initialPythons = 5;
    @Builder.Default
    private final int initialFoxes = 15;
    @Builder.Default
    private final int initialBears = 3;
    @Builder.Default
    private final int initialEagles = 8;

    // Травоядные
    @Builder.Default
    private final int initialHorses = 20;
    @Builder.Default
    private final int initialDeer = 25;
    @Builder.Default
    private final int initialRabbits = 100;
    @Builder.Default
    private final int initialMice = 200;
    @Builder.Default
    private final int initialGoats = 30;
    @Builder.Default
    private final int initialSheep = 30;
    @Builder.Default
    private final int initialBoars = 12;
    @Builder.Default
    private final int initialBuffaloes = 5;
    @Builder.Default
    private final int initialDucks = 40;
    @Builder.Default
    private final int initialCaterpillars = 500;

    // Растения
    @Builder.Default
    private final int initialPlantsPerCell = 10;  // при инициализации
    @Builder.Default
    private final int plantsGrowthPerTick = 1;    // сколько растёт за такт

    //  Параметры симуляции
    @Builder.Default
    private final long tickIntervalMs = 1000;     // интервал одного такта (мс)
    @Builder.Default
    private final int simulationTicks = 100;      // сколько тактов работать

    /**
     * Возвращает начальное количество животных указанного вида.
     */
    public int getInitialCount(Class<? extends javarush_simulation.Entity.Animal.Animal> animalClass) {
        if (animalClass == Wolf.class) return initialWolves;
        if (animalClass == Python.class) return initialPythons;
        if (animalClass == Fox.class) return initialFoxes;
        if (animalClass == Bear.class) return initialBears;
        if (animalClass == Eagle.class) return initialEagles;
        if (animalClass == Horse.class) return initialHorses;
        if (animalClass == Deer.class) return initialDeer;
        return 0;
    }

    /**
     * Возвращает массив всех типов животных, участвующих в симуляции.
     */
    public static Class<? extends javarush_simulation.Entity.Animal.Animal>[] getAnimalTypes() {

        return new Class[]{
                Wolf.class, Python.class, Fox.class, Bear.class, Eagle.class,
                Horse.class, Deer.class, Rabbit.class, Mouse.class,
                Goat.class, Sheep.class, Boar.class, Buffalo.class,
                Duck.class, Caterpillar.class
        };
    }
}
