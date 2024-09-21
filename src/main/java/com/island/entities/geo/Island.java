package main.java.com.island.entities.geo;

import main.java.com.island.entities.Animal;

public class Island {
    private static Cell[][] cells;  // Двовимірний масив клітинок
    private int rows;
    private int cols;

    public Island(int rows, int cols, int maxAnimalsPerCell, int maxPlantsPerCell) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[rows][cols];  // Ініціалізація масиву клітинок

        // Створюємо клітинки острова з обмеженнями на кількість тварин і рослин
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new Cell(maxAnimalsPerCell, maxPlantsPerCell);
            }
        }
    }

    // Отримуємо клітинку за координатами
    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    // Повертаємо кількість рядків (висота острова)
    public int getRows() {
        return rows;
    }

    // Повертаємо кількість стовпців (ширина острова)
    public int getCols() {
        return cols;
    }

    // Додаємо тварину на клітинку
    public static void placeAnimal(int row, int col, Animal animal) {
        cells[row][col].addAnimal(animal);
    }

    // Додаємо рослину на клітинку
    public void placePlant(int row, int col) {
        cells[row][col].addPlant();
    }
}
