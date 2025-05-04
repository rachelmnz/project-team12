import java.util.List;
import java.util.Scanner;

public class DebrisDensityAnalysis implements Analyzable {
    private final TrackingSystem tracker;

    public DebrisDensityAnalysis(TrackingSystem tracker, Scanner scanner) {
        this.tracker = tracker;
    }

    @Override
    public void analyze() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter minimum longitude: ");
        double min = scanner.nextDouble();
        System.out.print("Enter maximum longitude: ");
        double max = scanner.nextDouble();
        scanner.nextLine(); // flush newline
        scanner.close();

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
    }
}
