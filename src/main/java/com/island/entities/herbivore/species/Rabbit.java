package main.java.com.island.entities.herbivore.species;

import main.java.com.island.entities.Plant;
import main.java.com.island.entities.geo.Island;
import main.java.com.island.entities.herbivore.Herbivore;
import main.java.com.island.entities.predator.species.Wolf;
import main.java.com.island.services.Eatable;

import java.util.Random;

public class Rabbit extends Herbivore implements Eatable{
    public Rabbit(String name, double weight, int maxCountPerCell, int movementSpeed, double foodSaturation, String unicode) {
        super(name, weight, maxCountPerCell, movementSpeed, foodSaturation, unicode);
    }
    public Rabbit() {
        // Виклик конструктора з параметрами для травоїдного
        super("Rabbit", 2, 5, 3, 1, "\uD83D\uDC07");  // Ініціалізуємо кролика з параметрами за замовчуванням
    }

    @Override
    public void eat(Eatable food) {
        if (food instanceof Plant) {
            this.foodSaturation += food.getNutritionValue();  // Насичення збільшується
            this.gainWeight(food.getNutritionValue());  // Набирає вагу після поїдання рослини
            this.hasEatenToday = true;  // Встановлюємо, що тварина поїла сьогодні
          //  System.out.println(name + " has eaten a plant.");
        } else {
            System.out.println(name + " cannot eat this.");
        }
    }
@Override
    public void reproduce() {
        // Логіка розмноження !!!! Переробити
        if (age >= 2 && Math.random() < 0.3) {
            int offspringCount = (int) (Math.random() * 3) + 1;
            for (int i = 0; i < offspringCount; i++) {
                Rabbit offspring = new Rabbit();
                offspring.setPosition(xPosition, yPosition);
                Island.placeAnimal(xPosition,yPosition, offspring);
               // System.out.println("Rabbit reproduced.");
            }
        }
    }

    //@Override
    public synchronized void move(Island island) {
        Random random = new Random();
        int xOffset = random.nextInt(2 * movementSpeed + 1) - movementSpeed;
        int yOffset = random.nextInt(2 * movementSpeed + 1) - movementSpeed;

        int newX = Math.max(0, Math.min(xPosition + xOffset, island.getRows() - 1));
        int newY = Math.max(0, Math.min(yPosition + yOffset, island.getCols() - 1));
        //int newY = yOffset + yPosition;
        //int newX = xOffset +xPosition;

       // System.out.println(name + " (Rabbit) moved from (" + xPosition + ", " + yPosition + ") to (" + newX + ", " + newY + ")");
        xPosition = newX;
        yPosition = newY;
    }
}


