package main.java.com.island.entities;


import main.java.com.island.entities.geo.Island;
import main.java.com.island.services.Eatable;

public abstract class Animal {
        protected String name;
        protected double weight;
        protected int maxCountPerCell;
        protected int movementSpeed;
        protected double foodSaturation;
        protected int xPosition;
        protected int yPosition;
        protected boolean alive = true;
        public boolean hasEatenToday = false;
        private String unicode;
        protected int age = 0;

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public static int number = 0;

        public Animal(String name, double weight, int maxCountPerCell, int movementSpeed, double foodSaturation, String unicode) {
            this.name = name +   "-" + number++  ;
            this.weight = weight;
            this.maxCountPerCell = maxCountPerCell;
            this.movementSpeed = movementSpeed;
            this.foodSaturation = foodSaturation;
            this.unicode = unicode;
        }

        public String getUnicode() {
            return unicode;
        }

        public boolean isAlive() {
            return alive;
        }

        public void setAlive(boolean alive) {
            this.alive = alive;
        }

        public void setPosition(int x, int y) {
            this.xPosition = x;
            this.yPosition = y;
        }

        public int getXPosition() {
            return xPosition;
        }

        public int getYPosition() {
            return yPosition;
        }

    public String getName() {
        return name;
    }

    public abstract void eat(Eatable prey);

    // Логіка втрати ваги, якщо тварина не поїла

        public void loseWeightIfHungry() {
            double newWeight = weight - weight * 0.22; // Тварина втрачає % ваги
            if (newWeight <= 0.7 * weight) {
                alive = false;
                System.out.println(name + " has died of starvation.");
            }
            // }
        }
    // Відновлюємо тварину після дня, оновлюємо стан, чи вона їла
    public void resetForNextDay() {
        hasEatenToday = false;  // Скидаємо стан на новий день
    }

    // Збільшення ваги після їжі
    public void gainWeight(double nutritionValue) {
        weight += nutritionValue * 0.1;  // Тварина набирає 10% ваги від поживної цінності їжі
    }

        public abstract void move(Island island);
        public abstract void reproduce();
    }


