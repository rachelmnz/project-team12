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

        // Add test debris object for analysis demo
        /*tracker.addSpaceObject(new Debris(
            "T123", "TestDebris", "USA", "LEO",
            2010, "KSC", 45.0, 47.0, "abc123",
            500, 2
        ));*/

        UserManager userManager = new UserManager();
        SystemManager manager = new SystemManager(tracker, scanner, userManager);
        manager.loginMenu();

        // Exit message and log save
        LoggerUtil.log("Program terminated.");
        LoggerUtil.saveLatestLog();
    }
}
