package main.java.com.island.entities.predator.species;

import main.java.com.island.entities.Animal;
import main.java.com.island.entities.geo.Island;
import main.java.com.island.entities.herbivore.Herbivore;
import main.java.com.island.entities.predator.Predator;
import main.java.com.island.services.Eatable;

import java.util.Random;


public class Wolf extends Predator {
    public Wolf(String name, double weight, int maxCountPerCell, int movementSpeed, double foodSaturation, String unicode) {
        super(name, weight, maxCountPerCell, movementSpeed, foodSaturation, unicode);
    }

    public Wolf() {
        // Виклик конструктора з параметрами для хижака
        super("Wolf", 50, 5, 3, 8, "\uD83D\uDC3A");  // Ініціалізуємо вовка з параметрами за замовчуванням
    }
    @Override
    public void eat(Eatable prey) {
        if (prey instanceof Herbivore && Math.random() < 0.7) {  // 60% ймовірність успіху
            this.foodSaturation += prey.getNutritionValue();  // Насичення збільшується
            this.gainWeight(prey.getNutritionValue());  // Набирає вагу після поїдання
            this.hasEatenToday = true;  // Встановлюємо, що тварина поїла сьогодні
           // System.out.println(name + " has eaten " + ((Animal) prey).getName());
            ((Animal) prey).setAlive(false);

        } else {
          //  System.out.println(name + " missed the prey.");
        }
    }

    @Override
    public void reproduce() {
        // Логіка розмноження !!!! Переробити
       if (age >= 2 && Math.random() < 0.4) {
            int offspringCount = (int) (Math.random() * 3) + 1;
            for (int i = 0; i < offspringCount; i++) {
                Wolf offspring = new Wolf();
                offspring.setPosition(xPosition, yPosition);
                Island.placeAnimal(xPosition,yPosition, offspring);
               // System.out.println("Wolf reproduced.");

            }
        }
    }

    //@Override
    public synchronized void  move(Island island) {
        Random random = new Random();
        int xOffset = random.nextInt(2 * movementSpeed + 1) - movementSpeed;
        int yOffset = random.nextInt(2 * movementSpeed + 1) - movementSpeed;

        int newX = Math.max(0, Math.min(xPosition + xOffset, island.getRows() - 1));
        int newY = Math.max(0, Math.min(yPosition + yOffset, island.getCols() - 1));
        //int newY = yOffset + yPosition;
        //int newX = xOffset +xPosition;

        //System.out.println(name + " (Wolf) moved from (" + xPosition + ", " + yPosition + ") to (" + newX + ", " + newY + ")");
        xPosition = newX;
        yPosition = newY;
    }

}

