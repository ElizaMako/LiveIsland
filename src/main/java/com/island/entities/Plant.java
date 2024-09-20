package main.java.com.island.entities;

import main.java.com.island.services.Eatable;

public class Plant implements Eatable {
    private int nutritionValue;

    public Plant(){
        this.nutritionValue = 1;
    }

    public int getNutritionValue() {
        return nutritionValue;
    }
}
