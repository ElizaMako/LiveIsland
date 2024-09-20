package main.java.com.island.entities.predator.species;

import main.java.com.island.entities.Animal;
import main.java.com.island.entities.herbivore.Herbivore;
import main.java.com.island.entities.predator.Predator;
import main.java.com.island.services.Eatable;

public class Wolf extends Predator {
    public Wolf(String name, double weight, int maxCountPerCell, int movementSpeed, double foodSaturation, String unicode) {
        super(name, weight, maxCountPerCell, movementSpeed, foodSaturation, unicode);
    }

    public Wolf() {
        // Виклик конструктора з параметрами для хижака
        super("Wolf", 50, 30, 3, 8, "\uD83D\uDC3A");  // Ініціалізуємо вовка з параметрами за замовчуванням
    }
    @Override
    public void eat(Eatable prey) {
        if (prey instanceof Herbivore && Math.random() < 0.6) {
            System.out.println("Wolf has eaten " + ((Animal) prey).getName());
            foodSaturation += prey.getNutritionValue();
            ((Animal) prey).setAlive(false);
        } else {
            System.out.println("Wolf missed the prey.");
        }
    }

    @Override
    public void reproduce() {
        // Логіка розмноження
    }

    @Override
    public void move() {
        // Логіка переміщення
    }
}

