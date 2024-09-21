package main.java.com.island.entities;


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
        private String unicode;
        protected int age = 0;

        public Animal(String name, double weight, int maxCountPerCell, int movementSpeed, double foodSaturation, String unicode) {
            this.name = name;
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
        public abstract void move();
        public abstract void reproduce();
    }


