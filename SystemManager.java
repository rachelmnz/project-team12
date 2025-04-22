import java.util.Scanner;

public class SystemManager {

    private static final Scanner scanner = new Scanner(System.in);
    public static TrackingSystem tracker;

    public static void launch() {
        tracker = initializeSystem();

        while (true) {
            System.out.println("\nSelect User Type:");
            System.out.println("1. Scientist");
            System.out.println("2. Space Agency Representative");
            System.out.println("3. Policymaker");
            System.out.println("4. Administrator");
            System.out.println("5. EXIT");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleScientistMenu();
                    break;
                case "2":
                    handleSpaceAgencyMenu(); 
                    break;
                case "3":
                    handlePolicymakerMenu();
                    break;
                case "4":
                    handleAdminMenu();
                    break;
                case "5":
                case "EXIT":
                    System.out.println("Exiting system. Goodbye.");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static TrackingSystem initializeSystem() {
        TrackingSystem tracker = new TrackingSystem();
        FileHandler fileHandler = new FileHandler(tracker);
        fileHandler.loadData("debris_data.csv");

        System.out.println("Loaded objects:");
        for (SpaceObject obj : tracker.getAllObjects()) {
            obj.displayInfo();
        }

        return tracker;
    }

    private static void handlePolicymakerMenu() {
        while (true) {
            System.out.println("\nPolicymaker Menu:");
            System.out.println("1. Review Reports on Debris Impact");
            System.out.println("2. Assess Risk Levels for Future Space Missions");
            System.out.println("3. Back");
            System.out.print("Enter choice: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    reviewDebrisImpact();
                    break;
                case "2":
                    assessMissionRisk();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void handleAdminMenu() {
        while (true) {
            System.out.println("\nAdministrator Menu:");
            System.out.println("1. Create User");
            System.out.println("2. Manage User");
            System.out.println("3. Delete User");
            System.out.println("4. Back");
            System.out.print("Enter choice: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    createUser();
                    break;
                case "2":
                    manageUser();
                    break;
                case "3":
                    deleteUser();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void reviewDebrisImpact() {
        System.out.println("[TODO] Reviewing reports on debris impact...");
    }

    private static void assessMissionRisk() {
        System.out.println("[TODO] Assessing risk levels for future space missions...");
    }

    private static void createUser() {
        System.out.println("[TODO] Creating new user...");
    }

    private static void manageUser() {
        System.out.println("[TODO] Managing user...");
    }

    private static void deleteUser() {
        System.out.println("[TODO] Deleting user...");
    }
} 
