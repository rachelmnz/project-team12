import java.util.List;
import java.util.Scanner;
/**
*Analyzing the space object density based on the Longitude range
*/


public class DebrisDensityAnalysis implements Analyzable {
    private final TrackingSystem tracker;
    private final Scanner scanner;
/**
* Initializes with a tracker and scanner.
*
* @param tracker the tracking system
* @param scanner the input scanner
*/

    public DebrisDensityAnalysis(TrackingSystem tracker, Scanner scanner) {
        this.tracker = tracker;
        this.scanner = scanner;
    }
/**
*Prompts the user fro the Longitude range and shows the objects in the range input
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
