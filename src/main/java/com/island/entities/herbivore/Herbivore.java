package main.java.com.island.entities.herbivore;

import main.java.com.island.entities.Animal;
import main.java.com.island.entities.geo.Island;
import main.java.com.island.services.Eatable;

public class Herbivore extends Animal implements Eatable {
    public Herbivore(String name, double weight, int maxCountPerCell, int movementSpeed, double foodSaturation, String unicode) {
        super(name, weight, maxCountPerCell, movementSpeed, foodSaturation, unicode);
    }

    @Override
    public void eat(Eatable prey) {

    }

    @Override
    public void move(Island island) {

    }

    @Override
    public void reproduce() {
    }

    @Override
    public int getNutritionValue() {
        return 0;
    }
}
