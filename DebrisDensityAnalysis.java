import java.util.List;
import java.util.Scanner;

/**
*This Handles density analysis of space debrris objects that are based on
*Longitude range.
*Implements the Analyzable interface to support the consistent analysis behavior
*/ 

public class DebrisDensityAnalysis implements Analyzable {
    private final TrackingSystem tracker;
    private final Scanner scanner;
/**
*Constructs the DebrisDensityAnalysis object witht he tracking system and
*a scanner
*@param trakcer Tracking System contains all space objects.
*@param scanner it is used for user input
*/

    public DebrisDensityAnalysis(TrackingSystem tracker, Scanner scanner) {
        this.tracker = tracker;
        this.scanner = scanner;
    }

/**
*Promting the user to input a longitude range and will display all space objects
*their longitudes fall within the range, along with a count
*Handles invalid inputs
*/

    @Override
    public void analyze() {
        try {
            System.out.print("Enter minimum longitude: ");
            if (!scanner.hasNextDouble()) {
                System.out.println("Invalid input. Returning to menu.");
                scanner.nextLine();
                return;
            }
            double min = scanner.nextDouble();

            System.out.print("Enter maximum longitude: ");
            if (!scanner.hasNextDouble()) {
                System.out.println("Invalid input. Returning to menu.");
                scanner.nextLine();
                return;
            }
            double max = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("\nGenerating density report for objects between longitude " + min + " and " + max + "...\n");

            List<SpaceObject> objects = tracker.getAllObjects();
            int count = 0;
            for (SpaceObject obj : objects) {
                double lon = obj.getLongitude();
                if (lon >= min && lon <= max) {
                    count++;
                    System.out.printf("ID: %s | Name: %s | Country: %s | Orbit: %s | Launch Year: %d | Type: %s%n",
                            obj.getRecordId(), obj.getSatelliteName(), obj.getCountry(),
                            obj.getOrbitType(), obj.getLaunchYear(), obj.getClass().getSimpleName());
                }
            }

            System.out.printf("\nTotal objects in range: %d%n", count);
        } catch (Exception e) {
            System.out.println("Something went wrong during input or report generation.");
            scanner.nextLine();
        }
    }
}
