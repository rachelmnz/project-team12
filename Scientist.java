import java.util.Scanner;
import java.util.List; 

/**
 * Represents a Scientist user who can track space objects and assess orbit status.
 * Extends the {@code User} class.
 */
class Scientist extends User {
/**
* Constructs a Scientist with a scanner and tracking system.
*
* @param scanner the scanner for user input
* @param tracker the tracking system with space object data
*/
    public Scientist(Scanner scanner, TrackingSystem tracker) {
        super(scanner, tracker);
    }

 /**
* Displays the main menu for the Scientist, allowing the user to
* track objects or assess orbit status.
*/

    @Override
    public void displayMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n ----- Scientist Menu -----");
            System.out.println("1. Track the objects in space");
            System.out.println("2. Assess the orbit status");
            System.out.println("3. Back");

            String pick = scanner.nextLine();

            switch (pick) {
                case "1": trackObjectsMenu(); break;
                case "2": assessOrbitMenu(); break;
                case "3": back = true; break;
                default: System.out.println("Invalid input, please pick 1-3.");
            }
        }
    }
/**
* Displays a menu for tracking specific types of space objects.
*/

    private void trackObjectsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n ----- Track Objects in Space -----");
            System.out.println("1. Rocket Body\n2. Debris\n3. Payload\n4. Unknown\n5. Back");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": displayObjects("ROCKET BODY"); break;
                case "2": displayObjects("DEBRIS"); break;
                case "3": displayObjects("PAYLOAD"); break;
                case "4": displayObjects("UNKNOWN"); break;
                case "5": back = true; break;
                default: System.out.println("Invalid input.");
            }
        }
    }
/**
* Displays information for space objects of the given type.
*
* @param type the type of object to display (e.g., "DEBRIS")
*/

    private void displayObjects(String type) {
        List<SpaceObject> objs = tracker.getObjectsByType(type);
        System.out.println("\n--- " + type + " Details ---");
        for (SpaceObject obj : objs) {
            obj.displayInfo();
        }
    }
/**
* Displays a menu for assessing orbit status, including LEO tracking and
* debris evaluation.
*/
    private void assessOrbitMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n ----- Assess Orbit Status -----");
            System.out.println("1. Track Objects in LEO\n2. Assess Debris in Orbit\n3. Back");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": tracker.trackObjectsInLEO(); break;
                case "2":
                    tracker.assessDebrisInOrbit();
                    tracker.writeUpdatedOrbitCSV("updated_orbit_report.csv");
                    tracker.writeExitedDebrisReport("exited_debris_report.txt");
                    break;
                case "3": back = true; break;
                default: System.out.println("Invalid input.");
            }
        }
    }
}

