import java.util.List;
import java.util.Scanner;

/**
 * The {@code RunSimulation} class is the entry point of the simulation program.
 * It loads the data and delegates all interface handling to SystemManager.
 */
public class RunSimulation {

    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Load space object data from CSV
        List<SpaceObject> spaceObjects = FileHandler.loadSpaceObjects("rso_metrics.csv");
        TrackingSystem tracker = new TrackingSystem(spaceObjects);

        // Delegate to SystemManager
        SystemManager manager = new SystemManager(tracker, scanner);
        manager.startSimulation();

        // Exit message
        System.out.println("You are exiting the program. Have a good day!");
        scanner.close();
    }
}
