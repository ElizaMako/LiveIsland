package main.java.com.island.entities.geo;

import main.java.com.island.entities.Animal;
import main.java.com.island.entities.Plant;
import main.java.com.island.entities.herbivore.Herbivore;
import main.java.com.island.entities.predator.Predator;
import main.java.com.island.services.Simulation;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Cell {
    private static ConcurrentLinkedQueue<Animal> animals;

    private int plantCount;
    private static int maxAnimalsPerCell;
    private int maxPlantsPerCell;
    int count = 0;

    public Cell(int maxAnimalsPerCell, int maxPlantsPerCell) {
        animals = new ConcurrentLinkedQueue<>();
        this.plantCount = 0;
        Cell.maxAnimalsPerCell = maxAnimalsPerCell;
        this.maxPlantsPerCell = maxPlantsPerCell;
    }

    public ConcurrentLinkedQueue<Animal> getAnimals() {
        return animals;
    }

    public int getPlantCount() {
        return plantCount;
    }

    public  void addAnimal(Animal animal) {
        if (animals.size() < maxAnimalsPerCell) {
            animals.add(animal);
        }
    }

    public void addPlant() {
        if (plantCount < maxPlantsPerCell) {
            plantCount++;
        }
    }

    public synchronized void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public void removePlant() {
        if (plantCount > 0) {
            plantCount--;
        }
    }

    //логіка поїдання тварин на одній клітинці
    public void processInteractions(Island island, Simulation simulation) {
        // Розділимо тварин на хижаків і травоїдних
        ConcurrentLinkedQueue<Herbivore> herbivores = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Predator> predators = new ConcurrentLinkedQueue<>();


//
        for (Animal animal : animals) {
            if (animal instanceof Herbivore) {
                herbivores.add((Herbivore) animal);
            } else if (animal instanceof Predator) {
                predators.add((Predator) animal);
            }
        }

        //Переміщення
//        for (Herbivore herbivore : herbivores) {
//            if (herbivore.isAlive()) {
//                herbivore.move();
//            }
//        }
//        for (Predator predator : predators) {
//            if (predator.isAlive()) {
//                predator.move();
//            }
//        }

        // Поїдання травоїдних хижаками
        for (Predator predator : predators) {
            Herbivore herbivore = herbivores.peek();
            //for (Herbivore herbivore : herbivores) {
                if (predator.isAlive() && herbivore.isAlive()) {
                    predator.eat(herbivore);
//                    if (!herbivore.isAlive()) {
//                        removeAnimal(herbivore);
//                        count++;
//                        simulation.incrementEatenAnimalsCount();  // Підрахунок з'їдених тварин
//                        break;  // Вовк з'їв кролика — більше нічого не робимо з ним
//                    }
                    removeAnimal(herbivore);
                    count++;
                    simulation.incrementEatenAnimalsCount();
                }
            //}

        }

        // Поїдання рослин травоїдними
        if (plantCount > 0) {
            for (Herbivore herbivore : herbivores) {
                if (plantCount > 0) {
                    herbivore.eat(new Plant());  // Травоїдний їсть рослину
                    removePlant();
                }
            }
        }

        // Переміщення тварин після взаємодій!!!!!NEW METHOD! CHECK
        for (Animal animal : animals) {
            if (animal.isAlive()) {
                animal.move(island);  // Викликається специфічний метод move для кожної тварини
            }
        }
        // Після взаємодії тварин: перевіряємо їхній стан (чи поїли) і обробляємо втрату ваги
        for (Animal animal : animals) {
            if (animal.isAlive()) {
                animal.loseWeightIfHungry();// Якщо тварина не їла, вона втрачає вагу
                if (!animal.isAlive()) {
                    animals.remove(animal);
                } else {
                    animal.resetForNextDay();  // Скидаємо стан для нового дня
                }
            }
        }

        // Логіка розмноження, якщо тварини вижили після взаємодії
        for (Herbivore herbivore : herbivores) {
            if (herbivore.isAlive()) {
                herbivore.reproduce();
            }
        }
        for (Predator predator : predators) {
            if (predator.isAlive()) {
                predator.reproduce();
            }
        }

        ///переміщення????
//        for (Herbivore herbivore : herbivores) {
//            if (herbivore.isAlive()) {
//                herbivore.move();
//            }
//        }
//        for (Predator predator : predators) {
//            if (predator.isAlive()) {
//                predator.move();
//            }
//        }
       for (Animal animal : animals) {
           animal.hasEatenToday = false;
       }
    }
}
