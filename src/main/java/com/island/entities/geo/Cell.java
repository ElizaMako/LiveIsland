package main.java.com.island.entities.geo;

import main.java.com.island.entities.Animal;
import main.java.com.island.entities.Plant;
import main.java.com.island.entities.herbivore.Herbivore;
import main.java.com.island.entities.predator.Predator;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Cell {
    private ConcurrentLinkedQueue<Animal> animals;
    private int plantCount;
    private int maxAnimalsPerCell;
    private int maxPlantsPerCell;

    public Cell(int maxAnimalsPerCell, int maxPlantsPerCell) {
        this.animals = new ConcurrentLinkedQueue<>();
        this.plantCount = 0;
        this.maxAnimalsPerCell = maxAnimalsPerCell;
        this.maxPlantsPerCell = maxPlantsPerCell;
    }

    public ConcurrentLinkedQueue<Animal> getAnimals() {
        return animals;
    }

    public int getPlantCount() {
        return plantCount;
    }

    public void addAnimal(Animal animal) {
        if (animals.size() < maxAnimalsPerCell) {
            animals.add(animal);
        }
    }

    public void addPlant() {
        if (plantCount < maxPlantsPerCell) {
            plantCount++;
        }
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public void removePlant() {
        if (plantCount > 0) {
            plantCount--;
        }
    }

    //логіка поїдання тварин на одній клітинці
    public void processInteractions() {
        // Розділимо тварин на хижаків і травоїдних
        ConcurrentLinkedQueue<Herbivore> herbivores = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Predator> predators = new ConcurrentLinkedQueue<>();

        for (Animal animal : animals) {
            if (animal instanceof Herbivore) {
                herbivores.add((Herbivore) animal);
            } else if (animal instanceof Predator) {
                predators.add((Predator) animal);
            }
        }

        // Поїдання травоїдних хижаками
        for (Predator predator : predators) {
            for (Herbivore herbivore : herbivores) {
                if (predator.isAlive() && herbivore.isAlive()) {
                    predator.eat(herbivore);
                    if (!herbivore.isAlive()) {
                        removeAnimal(herbivore);
                        break;  // Вовк з'їв кролика — більше нічого не робимо з ним
                    }
                }
            }
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
    }
}
