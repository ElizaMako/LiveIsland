import main.java.com.island.config.ConfigLoader;
import main.java.com.island.services.Simulation;
import main.java.com.island.services.SimulationInitializer;

public class Main {
    public static void main(String[] args) {

        SimulationInitializer initializer = new SimulationInitializer();
        Simulation simulation = initializer.initializeSimulation();

        simulation.start();

    }
}