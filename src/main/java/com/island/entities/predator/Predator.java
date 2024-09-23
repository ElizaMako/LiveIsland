package main.java.com.island.entities.predator;

import main.java.com.island.entities.Animal;
import main.java.com.island.entities.geo.Island;
import main.java.com.island.services.Eatable;

import java.util.Random;

public class Predator extends Animal {
    public Predator(String name, double weight, int maxCountPerCell, int movementSpeed, double foodSaturation, String unicode) {
        super(name, weight, maxCountPerCell, movementSpeed, foodSaturation, unicode);
    }

    @Override
    public void eat(Eatable prey) {

    }

    @Override
    public void move(Island island) {
        Random random = new Random();
        int xOffset = random.nextInt(2 * movementSpeed + 1) - movementSpeed;
        int yOffset = random.nextInt(2 * movementSpeed + 1) - movementSpeed;

        int newX = Math.max(0, Math.min(xPosition + xOffset, island.getRows() - 1));
        int newY = Math.max(0, Math.min(yPosition + yOffset, island.getCols() - 1));
        //int newY = yOffset + yPosition;
        //int newX = xOffset +xPosition;

        System.out.println(name + "  moved from (" + xPosition + ", " + yPosition + ") to (" + newX + ", " + newY + ")");
        xPosition = newX;
        yPosition = newY;
    }

    @Override
    public void reproduce() {

    }

//    @Override
//    public void reproduce() {
//
//    }
}
