import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

/**
 * Manages the system's user login, authentication, and role-based menus.
 * Handles input for Scientists, Space Agency Representatives, Policymakers, and Administrators.
 */

public class SystemManager {
    private final TrackingSystem tracker;
    private final Scanner scanner;
    private final UserManager userManager;
    private UserManager.User currentUser;

/**
* Constructs a SystemManager with the given tracker, scanner, and user manager.
*
* @param tracker the space object tracking system
* @param scanner scanner for user input
* @param userManager handles user authentication and creation
*/

    public SystemManager(TrackingSystem tracker, Scanner scanner, UserManager userManager) {
        this.tracker = tracker;
        this.scanner = scanner;
        this.userManager = userManager;
    }
/**
* Sets the currently logged-in user.
*
* @param user the user who has successfully logged in
*/

    public void setCurrentUser(UserManager.User user) {
        this.currentUser = user;
    }
/**
* Displays the login menu and routes users to role-specific menus.
*/

    public void loginMenu() {
        boolean running = true;
        while (running) {
            LoggerUtil.log("Main menu displayed");
            System.out.println("\n===== Main Menu =====");
            System.out.println("1. Login as Scientist");
            System.out.println("2. Login as Space Agency Representative");
            System.out.println("3. Login as Administrator");
            System.out.println("4. Create New User");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");

            String input = scanner.nextLine();

            switch (input) {
                case "1", "2", "3" -> {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    UserManager.User user = userManager.authenticate(username, password);
                    if (user == null) {
                        System.out.println("Invalid credentials. Access denied.");
                    } else {
                        setCurrentUser(user);
                        switch (input) {
                            case "1" -> scientistMenu();
                            case "2" -> spaceAgencyMenu();
                            case "3" -> administratorMenu();
                        }
                    }
                }
                case "4" -> {
                    System.out.print("Enter new username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter new password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter role (Scientist, Space Agency Representative, Administrator): ");
                    String role = scanner.nextLine();
                    userManager.createUser(username, password, role);
                }
                case "EXIT", "exit", "Exit", "5" -> {
                    running = false;
                    System.out.println("You are exiting the program. Have a good day!");
                    scanner.close();
                }
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
/**
* Displays menu options specific to users with the Scientist role.
*/
    private void scientistMenu() {
        LoggerUtil.log("User '" + currentUser.getUsername() + "' entered Scientist menu");
        if (!currentUser.getRole().equalsIgnoreCase("Scientist")) {
            System.out.println("Access denied. You are not authorized to access this menu.");
            return;
        }
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

/**
* Displays menu options specific to Space Agency Representatives.
*/

    private void spaceAgencyMenu() {
        LoggerUtil.log("User '" + currentUser.getUsername() + "' entered Space Agency menu");
        if (!currentUser.getRole().equalsIgnoreCase("Space Agency Representative")) {
            System.out.println("Access denied. You are not authorized to access this menu.");
            return;
        }
        
        boolean back = false;
        while (!back) {
            System.out.println("\n ----- Space Agency Menu -----");
            System.out.println("1. Analyze the long term impact");
            System.out.println("2. Generate the Density Reports");
            System.out.println("3. Back");
            System.out.println("Please select an option (1-3).");

            String selection = safeReadLine();
            if (selection == null) return;

            if (selection.equals("1")) {
                System.out.println("Currently Analyzing the long term impact");
                tracker.analyzeLongTermImpact();
                //---------------------------------------------------------------------

            } else if (selection.equals("2")) {
                System.out.println("Currently generating the Density Reports");
                Scanner scanner = new Scanner(System.in);
                tracker.generateDensityReport(scanner);
                //----------------------------------------------------------------------

            } else if (selection.equals("3")) {
                back = true;
            } else {
                System.out.println("You input an invalid choice, please enter a choice from 1-3.");
            }
        }
    }
    private String safeReadLine() {
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }
    
    
/**
* Displays the menu for Administrators to manage users and view logs.
*/
    private void administratorMenu() {
        LoggerUtil.log("User '" + currentUser.getUsername() + "' entered Administrator menu");
        if (!currentUser.getRole().equalsIgnoreCase("Administrator")) {
            System.out.println("Access denied. You are not authorized to access this menu.");
            return;
        }
        boolean back = false;
        while (!back) {
            System.out.println("\n ----- Administrator Menu -----");
            System.out.println("1. Create User");
            System.out.println("2. Manage Users");
            System.out.println("3. Delete User");
            System.out.println("4. View Login Logs");
            System.out.println("5. Back");
            System.out.println("Please select an option (1-4)");
    
            String opt = scanner.nextLine();
    
            switch (opt) {
                case "1" -> {
                    while (true) {
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        if (userManager.getUser(username) != null) {
                            System.out.println("User already exists. Please enter a different username.");
                        } else {
                            System.out.print("Enter password: ");
                            String password = scanner.nextLine();
                            System.out.print("Enter role (Scientist, Space Agency Representative, Policymaker, Administrator): ");
                            String role = scanner.nextLine();
                            userManager.createUser(username, password, role);
                            break;
                        }
                    }
                }
                case "2" -> {
                    System.out.println("\n--- Current Users ---");
                    userManager.printAllUsers();
                
                    while (true) {
                        System.out.println("\n-----------------------------");
                        System.out.println(" What would you like to do?");
                        System.out.println("-----------------------------");
                        System.out.println("  [1] Manage an existing user");
                        System.out.println("  [2] Create a new user");
                        System.out.println("  [3] Delete a user");
                        System.out.println("  [4] Return to the Administrator Menu");
                        System.out.println("-----------------------------");
                        System.out.print("Select an option (1-4): ");
                        String subOpt = scanner.nextLine();
                
                        if (subOpt.equals("1")) {
                            System.out.print("Enter current username to manage: ");
                            String oldUsername = scanner.nextLine();
                
                            // Check if user exists
                            if (userManager.getUser(oldUsername) == null) {
                                System.out.println("User not detected");
                                continue; // loop back to menu
                            }
                
                            System.out.print("Enter new username: ");
                            String newUsername = scanner.nextLine();
                            System.out.print("Enter new password: ");
                            String newPassword = scanner.nextLine();
                            userManager.updateUser(oldUsername, newUsername, newPassword);
                            break;
                        } else if (subOpt.equals("2")) {
                            while (true) {
                                System.out.print("Enter username: ");
                                String username = scanner.nextLine();
                                if (userManager.getUser(username) != null) {
                                    System.out.println("User already exists. Please enter a different username.");
                                } else {
                                    System.out.print("Enter password: ");
                                    String password = scanner.nextLine();
                                    System.out.print("Enter role (Scientist, Space Agency Representative, Policymaker, Administrator): ");
                                    String role = scanner.nextLine();
                                    userManager.createUser(username, password, role);
                                    break;
                                }
                            }
                            break;
                        } else if (subOpt.equals("3")) {
                            System.out.print("Enter username to delete: ");
                            String usernameToDelete = scanner.nextLine();
                            userManager.deleteUser(usernameToDelete);
                            break;
                        } else if (subOpt.equals("4")) {
                            System.out.println("Returning to Administrator menu...");
                            break;
                        } else {
                            System.out.println("There is no option for your input");
                        }
                    }
                }
                case "3" -> {
                    userManager.printAllUsers();
                    System.out.print("Enter the username of the user to delete: ");
                    String usernameToDelete = scanner.nextLine();
                    userManager.deleteUser(usernameToDelete);
                }
                case "4" -> {
                    System.out.println("\n--- User Login Log ---");
                    try {
                        List<String> lines = Files.readAllLines(Paths.get("log.txt"));
                        Map<String, Integer> scientistCount = new HashMap<>();
                        Map<String, Integer> agencyCount = new HashMap<>();
                        Map<String, Integer> policymakerCount = new HashMap<>();
                        Map<String, Integer> adminCount = new HashMap<>();
                        Map<String, Integer> mainMenuCount = new HashMap<>();

                        for (String line : lines) {
                            if (line.contains("logged in as Scientist")) {
                                String user = extractUsername(line);
                                scientistCount.put(user, scientistCount.getOrDefault(user, 0) + 1);
                            } else if (line.contains("logged in as Space Agency Representative")) {
                                String user = extractUsername(line);
                                agencyCount.put(user, agencyCount.getOrDefault(user, 0) + 1);
                            } else if (line.contains("logged in as Policy Maker") || line.contains("logged in as Policymaker")) {
                                String user = extractUsername(line);
                                policymakerCount.put(user, policymakerCount.getOrDefault(user, 0) + 1);
                            } else if (line.contains("logged in as Administrator")) {
                                String user = extractUsername(line);
                                adminCount.put(user, adminCount.getOrDefault(user, 0) + 1);
                            } else if (line.contains("Main menu displayed")) {
                                String user = currentUser != null ? currentUser.getUsername() : "Unknown";
                                mainMenuCount.put(user, mainMenuCount.getOrDefault(user, 0) + 1);
                            }
                        }

                        printLogCount("Scientist", scientistCount);
                        printLogCount("Space Agency Representative", agencyCount);
                        printLogCount("Policymaker", policymakerCount);
                        printLogCount("Administrator", adminCount);
                        printLogCount("Main Menu Visits", mainMenuCount);

                    } catch (IOException e) {
                        System.out.println("Failed to read log file.");
                        e.printStackTrace();
                    }
                }
                case "5" -> {
                    LoggerUtil.log("User '" + currentUser.getUsername() + "' returned to main menu from Administrator");
                    back = true;
                }
                default -> System.out.println("Invalid input, please choose an option from 1-5");
            }
        }
    }
/**
* Extracts the username from a log entry line.
*
* @param logLine the log line containing a username
* @return the extracted username or "Unknown"
*/
        private static String extractUsername(String logLine) {
            int start = logLine.indexOf("User '") + 6;
            int end = logLine.indexOf("' logged in");
            return (start >= 6 && end > start) ? logLine.substring(start, end) : "Unknown";
        }
/**
* Prints the usage count from a user role map.
*
* @param title the section title
* @param map   the username-to-count map
*/
        private static void printLogCount(String title, Map<String, Integer> map) {
            System.out.println("\n-- " + title + " --");
            if (map.isEmpty()) {
            System.out.println("No records found.");
            } else {
                map.forEach((user, count) -> System.out.println(user + ": " + count));
        }
    }   
}
