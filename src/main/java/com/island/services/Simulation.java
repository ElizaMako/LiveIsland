package main.java.com.island.services;

import main.java.com.island.entities.Animal;
import main.java.com.island.entities.IslandObjects;
import main.java.com.island.entities.geo.Cell;
import main.java.com.island.entities.geo.Island;
import main.java.com.island.entities.herbivore.species.Rabbit;
import main.java.com.island.entities.predator.species.Wolf;
import main.java.com.island.gui.IslandGUI;

import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class Simulation {
    private Island island;
    private IslandGUI gui;
    private int dayLength;

    public Simulation(IslandGUI gui, int rows, int cols, int maxAnimalsPerCell, int maxPlantsPerCell) {
        this.island = new Island(rows, cols, maxAnimalsPerCell, maxPlantsPerCell);
        this.gui = gui;
    }

    public void start() {
        // Автоматизоване розміщення тварин і рослин
        placeInitialAnimalsAndPlants();

        // Запуск потоків для симуляції (цикли симуляції)
    }

    // Автоматизоване розміщення тварин і рослин
    private void placeInitialAnimalsAndPlants() {
        // Створимо карту типів тварин і кількості для автоматичного розміщення
        Map<Supplier<Animal>, Integer> animalTypes = Map.of(
                Wolf::new, 30,
                Rabbit::new, 30

                //Fox::new, 20,
                //Buffalo::new, 15
                // Додаємо інші види тварин за необхідності
        );

        // Розміщення тварин за допомогою рандомних координат
        for (Map.Entry<Supplier<Animal>, Integer> entry : animalTypes.entrySet()) {
            placeAnimalsRandomly(entry.getKey(), entry.getValue());
        }

        // Розміщення рослин
        placePlantsRandomly(100);  // Наприклад, 100 рослин
    }

    // Метод для рандомного розміщення тварин
    private void placeAnimalsRandomly(Supplier<Animal> animalSupplier, int numberOfAnimals) {
        Random random = new Random();
        int rows = island.getRows();
        int cols = island.getCols();

        for (int i = 0; i < numberOfAnimals; i++) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);

            // Додаємо тварину на випадкову клітинку
            Animal animal = animalSupplier.get();
            island.placeAnimal(row, col, animal);

            // Оновлення GUI для кожної клітинки
            gui.updateCell(row, col, animal.getUnicode());
        }
    }

    // Метод для рандомного розміщення рослин
    private void placePlantsRandomly(int numberOfPlants) {
        Random random = new Random();
        int rows = island.getRows();
        int cols = island.getCols();

        for (int i = 0; i < numberOfPlants; i++) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);

            // Додаємо рослину на випадкову клітинку
            island.placePlant(row, col);

            // Оновлення GUI для кожної клітинки
            gui.updateCell(row, col, IslandObjects.PLANT.getOneUnicode());
        }
    }

    // Цикл симуляції
    public void runSimulationCycle() {
        for (int row = 0; row < island.getRows(); row++) {
            for (int col = 0; col < island.getCols(); col++) {
                Cell cell = island.getCell(row, col);
                cell.processInteractions();  // Логіка взаємодії на клітинці
            }
        }

        // Оновлення GUI після кожного циклу
        updateGUI();
    }

    // Оновлення GUI для кожної клітинки
    private void updateGUI() {
        for (int row = 0; row < island.getRows(); row++) {
            for (int col = 0; col < island.getCols(); col++) {
                Cell cell = island.getCell(row, col);

                // Оновлюємо кожну клітинку в GUI
                if (cell.getAnimals().size() > 0) {
                    Animal topAnimal = cell.getAnimals().peek();  // Беремо одну тварину для відображення
                    gui.updateCell(row, col, topAnimal.getUnicode());
                } else if (cell.getPlantCount() > 0) {
                    gui.updateCell(row, col, IslandObjects.PLANT.getOneUnicode());
                } else {
                    gui.updateCell(row, col, "");  // Порожня клітинка
                }
            }
        }
    }

    public void setDayLength(int dayLength) {
        this.dayLength = dayLength;
    }
}
