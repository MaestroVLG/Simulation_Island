package javarush_simulation.Entity.Animal.factory;

import javarush_simulation.Entity.Animal.Animal;
import javarush_simulation.Entity.Animal.implementations.*;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Фабрика для создания животных.
 * Потокобезопасна, использует рефлексию и картирование типов.
 */
public class AnimalFactory {

    @SuppressWarnings("unchecked")
    private static final Map<Class<? extends Animal>, AnimalCreator> CREATORS = Map.ofEntries(
            Map.entry(Wolf.class, (AnimalCreator) Wolf::new),
            Map.entry(Python.class, (AnimalCreator) Python::new),
            Map.entry(Fox.class, (AnimalCreator) Fox::new),
            Map.entry(Bear.class, (AnimalCreator) Bear::new),
            Map.entry(Eagle.class, (AnimalCreator) Eagle::new),
            Map.entry(Horse.class, (AnimalCreator) Horse::new),
            Map.entry(Deer.class, (AnimalCreator) Deer::new),
            Map.entry(Rabbit.class, (AnimalCreator) Rabbit::new),
            Map.entry(Mouse.class, (AnimalCreator) Mouse::new),
            Map.entry(Goat.class, (AnimalCreator) Goat::new),
            Map.entry(Sheep.class, (AnimalCreator) Sheep::new),
            Map.entry(Boar.class, (AnimalCreator) Boar::new),
            Map.entry(Buffalo.class, (AnimalCreator) Buffalo::new),
            Map.entry(Duck.class, (AnimalCreator) Duck::new),
            Map.entry(Caterpillar.class, (AnimalCreator) Caterpillar::new)
    );

    @FunctionalInterface
    public interface AnimalCreator {
        Animal create();
    }

    /**
     * Создаёт новое животное указанного типа.
     *
     * @param animalClass класс животного
     * @return новый экземпляр
     * @throws IllegalArgumentException если тип не поддерживается
     */
    public static Animal create(Class<? extends Animal> animalClass) {
        if (animalClass == null) {
            throw new IllegalArgumentException("Класс животного не может быть null");
        }
        AnimalCreator creator = CREATORS.get(animalClass);
        if (creator == null) {
            throw new IllegalArgumentException("Тип животного не поддерживается: " + animalClass.getSimpleName());
        }
        try {
            return creator.create();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать экземпляр животного: " + animalClass.getSimpleName(), e);
        }
    }

    /**
     * Создаёт случайное животное из доступных видов.
     *
     * @return случайное животное
     */
    public static Animal createRandom() {
        var classes = CREATORS.keySet().toArray(new Class[0]);
        Class<? extends Animal> randomClass = classes[ThreadLocalRandom.current().nextInt(classes.length)];
        return create(randomClass);
    }

    /**
     * Проверяет, поддерживается ли данный класс животного.
     */
    public static boolean supports(Class<? extends Animal> animalClass) {
        return CREATORS.containsKey(animalClass);
    }
}