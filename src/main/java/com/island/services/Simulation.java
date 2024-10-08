package main.java.com.island.services;

import main.java.com.island.entities.Animal;
import main.java.com.island.entities.IslandObjects;
import main.java.com.island.entities.geo.Cell;
import main.java.com.island.entities.geo.Island;
import main.java.com.island.entities.herbivore.Herbivore;
import main.java.com.island.entities.herbivore.species.Mouse;
import main.java.com.island.entities.herbivore.species.Rabbit;
import main.java.com.island.entities.predator.Predator;
import main.java.com.island.entities.predator.species.Fox;
import main.java.com.island.entities.predator.species.Wolf;
import main.java.com.island.gui.IslandGUI;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class Simulation {
    private Island island;
    private IslandGUI gui;
    private int dayLength;
    private ScheduledExecutorService animalLifeCycleScheduler;
    private ScheduledExecutorService plantGrowthScheduler;
    private ScheduledExecutorService statisticsScheduler;
    private int eatenAnimalsCount = 0;  // Лічильник з'їдених тварин

    public Simulation(IslandGUI gui, int rows, int cols, int maxAnimalsPerCell, int maxPlantsPerCell) {
        this.island = new Island(rows, cols, maxAnimalsPerCell, maxPlantsPerCell);
        this.gui = gui;
        this.animalLifeCycleScheduler = Executors.newScheduledThreadPool(4);
        this.plantGrowthScheduler = Executors.newScheduledThreadPool(2);
        this.statisticsScheduler = Executors.newScheduledThreadPool(4);
    }

    public void start() {
        // Автоматизоване розміщення тварин і рослин
        placeInitialAnimalsAndPlants();


        // Запуск потоків для симуляції (цикли симуляції)
        startSimulationThreads();
    }

    // Автоматизоване розміщення тварин і рослин
    private void placeInitialAnimalsAndPlants() {
        // Створимо карту типів тварин і кількості для автоматичного розміщення
        Map<Supplier<Animal>, Integer> animalTypes = Map.of(
                Wolf::new, 3,
                Rabbit::new, 15,
                Mouse::new, 10,
                Fox::new,2

                //Fox::new, 20,
                //Buffalo::new, 15
                // TODO Додати інші види тварин пізніше
        );

        // Розміщення тварин за допомогою рандомних координат
        for (Map.Entry<Supplier<Animal>, Integer> entry : animalTypes.entrySet()) {
            placeAnimalsRandomly(entry.getKey(), entry.getValue());
        }

        // Розміщення рослин
        placePlantsRandomly(150);  // Наприклад, 100 рослин
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
            Island.placeAnimal(row, col, animal);

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

    private void startSimulationThreads() {
        animalLifeCycleScheduler.scheduleAtFixedRate(this::runSimulationCycle, 4, 1, TimeUnit.SECONDS);
        statisticsScheduler.scheduleAtFixedRate(this::printStatistics, 0, 1, TimeUnit.SECONDS);
        plantGrowthScheduler.scheduleAtFixedRate(this::growPlants, 0, 1, TimeUnit.SECONDS);

    }

    // Цикл симуляції
    public void runSimulationCycle() {
        for (int row = 0; row < island.getRows(); row++) {
            for (int col = 0; col < island.getCols(); col++) {
                Cell cell = island.getCell(row, col);
                cell.processInteractions(island, this);  // Логіка взаємодії на клітинці
            }
        }

        // Оновлення GUI після кожного циклу
        updateGUI();


    }

    private void growPlants() {
        System.out.println("Plants are growing...");
        for (int row = 0; row < island.getRows(); row++) {
            for (int col = 0; col < island.getCols(); col++) {
                island.getCell(row, col).addPlant();
                //gui.updateCell(row, col, IslandObjects.PLANT.getOneUnicode());
            }
        }
    }

    // Оновлення GUI для кожної клітинки
    private void updateGUI() {
        for (int row = 0; row < island.getRows(); row++) {
            for (int col = 0; col < island.getCols(); col++) {
                Cell cell = island.getCell(row, col);

                // Оновлюємо кожну клітинку в GUI
                if (!cell.getAnimals().isEmpty()) {

                    Animal topAnimal = cell.getAnimals().peek();  // Беремо одну тварину для відображення
                    gui.updateCell(row, col, topAnimal.getUnicode());
                //} else if (cell.getPlantCount() > 0) {
                //    gui.updateCell(row, col, IslandObjects.PLANT.getOneUnicode());
                } else {
                    gui.updateCell(row, col, "");  // Порожня клітинка
                }
            }
        }
    }
    // Збільшення кількості з'їдених тварин
    public void incrementEatenAnimalsCount() {
        eatenAnimalsCount++;
    }

    public void setDayLength(int dayLength) {
        this.dayLength = dayLength;
    }
    private void printStatistics() {
        int totalAnimals = 0;
        int totalPlants = 0;
        int totalPredators = 0;
        int totalHerbivores = 0;

        for (int row = 0; row < island.getRows(); row++) {
            for (int col = 0; col < island.getCols(); col++) {
                Cell cell = island.getCell(row, col);
                totalAnimals += cell.getAnimals().size();
                totalPlants += cell.getPlantCount();

                for (Animal animal : cell.getAnimals()) {
                    if (animal instanceof Predator) {
                        totalPredators++;
                    } else if (animal instanceof Herbivore) {
                        totalHerbivores++;
                    }
                }
            }
        }

        System.out.println("----- Simulation Statistics -----");
        System.out.println("Total Animals: " + totalAnimals);
        System.out.println("Total Predators: " + totalPredators);
        System.out.println("Total Herbivores: " + totalHerbivores);
        System.out.println("Total Plants: " + totalPlants);
        System.out.println("Total Eaten Animals: " + eatenAnimalsCount);  // Новий рядок у статистиці
        System.out.println("----------------------------------");

        System.out.println();
    }

    public void stopSimulation() {
        animalLifeCycleScheduler.shutdown();
        plantGrowthScheduler.shutdown();
        statisticsScheduler.shutdown();
    }
}

