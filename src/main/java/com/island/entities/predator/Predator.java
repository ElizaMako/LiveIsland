package main.java.com.island.entities.predator;

import main.java.com.island.entities.Animal;
import main.java.com.island.services.Eatable;

public class Predator extends Animal{
    public Predator(String name, double weight, int maxCountPerCell, int movementSpeed, double foodSaturation, String unicode) {
        super(name, weight, maxCountPerCell, movementSpeed, foodSaturation, unicode);
    }

    @Override
    public void eat(Eatable prey) {

    }

    @Override
    public void move() {

    }

    @Override
    public void reproduce() {

    }
}
