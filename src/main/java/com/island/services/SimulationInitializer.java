package main.java.com.island.services;

import main.java.com.island.config.ConfigLoader;
import main.java.com.island.gui.IslandGUI;

public class SimulationInitializer {
    private ConfigLoader config;

    public SimulationInitializer() {
        this.config = new ConfigLoader();
    }
    public Simulation initializeSimulation() {
int dayLength = Integer.parseInt(config.getProperty("day.length"));
int maxAnimalsPerCell = Integer.parseInt(config.getProperty("max.animals.per.cell"));
int maxPlantsPerCell = Integer.parseInt(config.getProperty("max.plants.per.cell"));

        IslandGUI gui = new IslandGUI(3,3);

        Simulation simulation = new Simulation(gui, 3,3,maxAnimalsPerCell,maxPlantsPerCell);

        simulation.setDayLength(dayLength);

        return simulation;
    }
}
