package main.java.com.island.entities.herbivore.species;

import main.java.com.island.entities.Plant;
import main.java.com.island.entities.herbivore.Herbivore;
import main.java.com.island.services.Eatable;

public class Rabbit extends Herbivore {
    public Rabbit(String name, double weight, int maxCountPerCell, int movementSpeed, double foodSaturation, String unicode) {
        super(name, weight, maxCountPerCell, movementSpeed, foodSaturation, unicode);
    }
    public Rabbit() {
        // Виклик конструктора з параметрами для травоїдного
        super("Rabbit", 2, 250, 3, 1, "\uD83D\uDC07");  // Ініціалізуємо кролика з параметрами за замовчуванням
    }

    @Override
    public void eat(Eatable food) {
        if (food instanceof Plant) {
            this.foodSaturation += food.getNutritionValue();
            System.out.println("Rabbit has eaten a plant.");
        } else {
            System.out.println("Rabbit cannot eat this.");
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


