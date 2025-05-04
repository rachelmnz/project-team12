import java.util.List;
import java.util.Scanner;

public class SystemManager {
    private final TrackingSystem tracker;
    private final Scanner scanner;

    public SystemManager(TrackingSystem tracker, Scanner scanner) {
        this.tracker = tracker;
        this.scanner = scanner;
    }

    public void startSimulation() {
        boolean exitProgram = false;

        while (!exitProgram) {
            System.out.println("\n-----Main Menu-----");
            System.out.println("1. Scientist");
            System.out.println("2. Space Agency Representative");
            System.out.println("3. Policy Maker");
            System.out.println("4. Administrator");
            System.out.println("5. Exit");
            System.out.println("Welcome, PLease select your user type (1-5): ");

            String input = scanner.nextLine();
            String userType = input.toUpperCase();

            if (userType.equals("EXIT") || userType.equals("5")) {
                exitProgram = true;
            } else if (userType.equals("1")) {
                scientistMenu();
            } else if (userType.equals("2")) {
                spaceAgencyMenu();
            } else if (userType.equals("3")) {
                policyMakerMenu();
                System.out.println("We are working on the policy maker menu");
            } else if (userType.equals("4")) {
                administratorMenu();
                System.out.println("We are currently working on the Administrator menu");
            } else {
                System.out.println("You have input an invalid number pleace chose a number from 1-5");
            }
        }
    }

    private void scientistMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n ----- Scientist Menu -----");
            System.out.println("1. Track the objects in space");
            System.out.println("2. Assess the orbit status");
            System.out.println("3. Back");
            System.out.println("Please select an option (1-3)");

            String pick = scanner.nextLine();

            if (pick.equals("1")) {
                boolean backOne = false;
                while (!backOne) {
                    System.out.println("\n ----- Track Objects in Space -----");
                    System.out.println("1. Rocket Body");
                    System.out.println("2. Debris");
                    System.out.println("3. Payload");
                    System.out.println("4. Unknown");
                    System.out.println("5. Back");
                    System.out.println("Please select an option (1-5)");
                    String choice = scanner.nextLine();

                    if (choice.equals("1")) {
                        List<SpaceObject> rockets = tracker.getObjectsByType("ROCKET BODY");
                        System.out.println("\n--- Rocket Body Details ---");
                        for (SpaceObject rocket : rockets) {
                            System.out.println("Rocket ID: " + rocket.getRecordId() + ", Name: " + rocket.getSatelliteName() + ", Country: " + rocket.getCountry());
                            rocket.displayInfo();
                        }
                    } else if (choice.equals("2")) {
                        List<SpaceObject> debris = tracker.getObjectsByType("DEBRIS");
                        System.out.println("\n--- Debris Details ---");
                        for (SpaceObject d : debris) {
                            d.displayInfo();
                        }
                    } else if (choice.equals("3")) {
                        List<SpaceObject> payloads = tracker.getObjectsByType("PAYLOAD");
                        System.out.println("\n--- Payload Details ---");
                        for (SpaceObject p : payloads) {
                            p.displayInfo();
                        }
                    } else if (choice.equals("4")) {
                        List<SpaceObject> unknowns = tracker.getObjectsByType("UNKNOWN");
                        System.out.println("\n--- Unknown Object Details ---");
                        for (SpaceObject u : unknowns) {
                            u.displayInfo();
                        }
                    } else if (choice.equals("5")) {
                        backOne = true;
                    } else {
                        System.out.println("Invalid choice, please select an option between 1-5");
                    }
                }
            } else if (pick.equals("2")) {
                boolean backTwo = false;
                while (!backTwo) {
                    System.out.println("\n ----- Assess the Orbit Status -----");
                    System.out.println("1. Track Objects in LEO");
                    System.out.println("2. Assess if debris is still in orbit");
                    System.out.println("3. Back");
                    System.out.println("Please select an option (1-3)");
                    String choice = scanner.nextLine();

                    if (choice.equals("1")) {
                        tracker.trackObjectsInLEO();
                    } else if (choice.equals("2")) {
                        tracker.assessDebrisInOrbit();
                        tracker.writeUpdatedOrbitCSV("updated_orbit_report.csv");
                        tracker.writeExitedDebrisReport("exited_debris_report.txt");

                    } else if (choice.equals("3")) {
                        backTwo = true;
                    } else {
                        System.out.println("Invalid choice, please select an option between 1-3");
                    }
                }
            } else if (pick.equals("3")) {
                back = true;
            } else {
                System.out.println("You have chosen an invalid input please pick an option from 1-3.");
            }
        }
    }

    private void spaceAgencyMenu() {
        AnalysisContext context = new AnalysisContext();
        boolean back = false;
        while (!back) {
            System.out.println("\n ----- Space Agency Menu -----");
            System.out.println("1. Analyze the long term impact");
            System.out.println("2. Generate the Density Reports");
            System.out.println("3. Back");
            System.out.println("Please select an option (1-3).");

            String selection = scanner.nextLine();

            if (selection.equals("1")) {
                System.out.println("Currently Analyzing the long term impact");
                context.setStrategy(new ImpactAnalysis(tracker));
                context.execute();

            } else if (selection.equals("2")) {
                System.out.println("Currently generating the Density Reports");
                context.setStrategy(new DebrisDensityAnalysis(tracker, scanner));
                context.execute();

            } else if (selection.equals("3")) {
                back = true;
            } else {
                System.out.println("You input an invalid choice, please enter a choice from 1-3.");
            }
        }
    }

    private void policyMakerMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n ----- Policymaker Menu -----");
            System.out.println("1. Review the reports on the Debris Impact");
            System.out.println("2. Assess the risk levels for future space missions");
            System.out.println("3. Back");
            System.out.println("Please select an option from (1-3)");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.println("[TODO] Reviewing reports on debris impact...");
            } else if (choice.equals("2")) {
                System.out.println("[TODO] Assessing risk levels for future space missions...");
            } else if (choice.equals("3")) {
                back = true;
            } else {
                System.out.println("Invalis input, please input a choice of 1-3");
            }
        }
    }

    private void administratorMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n ----- Administrator Menu -----");
            System.out.println("1. Create User");
            System.out.println("2. Manage Users");
            System.out.println("3. Delete User");
            System.out.println("4. Back");
            System.out.println("Please select an option (1-4)");

            String opt = scanner.nextLine();

            if (opt.equals("1")) {
                System.out.println("[TODO] Creating new user...");
            } else if (opt.equals("2")) {
                System.out.println("[TODO] Managing user...");
            } else if (opt.equals("3")) {
                System.out.println("[TODO] Deleting user...");
            } else if (opt.equals("4")) {
                back = true;
            } else {
                System.out.println("Invalid input, please choose an option from 1-4");
            }
        }
    }
}
