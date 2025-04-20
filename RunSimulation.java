import java.util.Scanner;

public class RunSimulation {

    public static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        TrackingSystem tracker = new TrackingSystem();
        FileHandler fileHandler = new FileHandler(tracker);
        fileHandler.loadData("debris_data.csv");

        System.out.println("Loaded objects:");
        for (SpaceObject obj : tracker.getAllObjects()) {
            obj.displayInfo();
        }
        boolean exitProgram = false;

        while(!exitProgram){
            System.out.println("/n-----Main Menu-----");
            System.out.println("1. Scientist");
            System.out.println("2. Space Agency Representative");
            System.out.println("3. Policy Maker");
            System.out.println("4. Administrator");
            System.out.println("5. Exit");
            System.out.println("Welcome, PLease select your user type (1-5): ");

            String input = scanner.nextLine();
            String userType = input.toUpperCase();

            if(userType.equals("EXIT")){
                tracker.writeUpdatedReport("This is the updated debrisreport.csv");
                //fileHandler.savelog("simulation.log");
                exitProgram = true;
            }else if (userType == "1"){
                scientistMenu(scanner, tracker);
            }else if (userType == "2"){
                spaceAgencyMenu(scanner, tracker);
            }else if (userType == "3"){
                policyMakerMenu(scanner, tracker);
                System.out.println("We are working on the policy maker menu");
            }else if (userType == "4"){
                administratorMenu(scanner, tracker);
                System.out.println("We are currently working on the Administrator menu");
            }else if (userType == "5"){
                tracker.writeUpdatedReport("updated debris report.csv");
                //fileHandler.savelog("simulation.log");
                exitProgram = true;
            }else{
                System.out.println("You have input an invalid number pleace chose a number from 1-5");
            }

        }
        System.out.println("You are exiting the program. Have a good day!");
        scanner.close();
    }
    private static void scientistMenu(Scanner scanner, TrackingSystem tracker){
        boolean back = false;
        while (!back){
            System.out.println("\n ----- Scientist Menu -----");
            System.out.println("1. Track the objects in space");
            System.out.println("2. Assess the orbit status");
            System.out.println("3. Back");
            System.out.println("Please select an option (1-3)");

            String pick = scanner.nextLine();

            if (pick == "1"){
                boolean backOne = false;
                while(!backOne){
                    System.out.println("\n ----- Track Objects in Space -----");
                    System.out.println("1. Rocket Body");
                    System.out.println("2. Debris");
                    System.out.println("3. Payload");
                    System.out.println("4. Unknown");
                    System.out.println("5. Back");
                    System.out.println("Please select an option (1-5)");
                    String choice = scanner.nextLine();

                    if(choice == "1"){

                    } else if(choice == "2"){

                    } else if(choice == "3"){

                    } else if(choice == "4"){

                    } else if(choice == "5"){
                        backOne = true;
                    } else {
                        System.out.println("Invalid choice, please select an option between 1-5");
                    }
                }

            }else if (pick == "2"){
                boolean backTwo = false;
                while(!backTwo){
                    System.out.println("\n ----- Assess the Orbit Status -----");
                    System.out.println("1. Track Objects in LEO");
                    System.out.println("2. Assess if debris is still in orbit");
                    System.out.println("3. Back");
                    System.out.println("Please select an option (1-3)");
                    String choice = scanner.nextLine();

                    if(choice == "1"){
                        tracker.trackObjectsInSpace();

                    } else if(choice == "2"){
                        tracker.assessOrbitStatus();

                    } else if(choice == "3"){
                        backTwo = true;
                    } else {
                        System.out.println("Invalid choice, please select an option between 1-3");
                    }
                }

            }else if (pick == "3"){
                back = true;
            }else{
                System.out.println("You have chosen an invalid input please pick an option form 1-3.");
            }
        }
    }

    private static void spaceAgencyMenu(Scanner scanner, TrackingSystem tracker){
        boolean back = false;
        while(!back){
            System.out.println("\n ----- Space Agency Menu -----");
            System.out.println("1. Analyze the long term impact");
            System.out.println("2. Generate the Density Reports");
            System.out.println("3. Back");
            System.out.println("Please select an option (1-3).");

            String selection = scanner.nextLine();

            if (selection == "1"){

                System.out.println("Currently Analyzing the long term impact");
            }else if (selection == "2"){

                System.out.println("Currently generating the Density Reports");
            }else if (selection == "3"){
                back = true;
            }else{
                System.out.println("You input an invalid choice, please enter a choice from 1-3.");
            }
        }
    }

    private static void policyMakerMenu( Scanner scanner,TrackingSystem tracker){
        boolean back = false;
        while(!back){
            System.out.println("\n ----- Policymaker Menu -----");
            System.out.println("1. Review the reports on the Debris Impact");
            System.out.println("2. Assess the risk levels for future space missions");
            System.out.println("3. Back");
            System.out.println("Please select an option from (1-3)");

            String choice = scanner.nextLine();

            if(choice == "1"){
                reviewDebrisImpact();
            }else if (choice == "2"){
                assessMissionRisk();
            }else if (choice =="3"){
                back = true;
            }else{
                System.out.println("Invalis input, please input a choice of 1-3");
            }
        }
    }

    private static void administratorMenu(Scanner scanner, TrackingSystem tracker){
        boolean back = false;
        while(!back){
            System.out.println("\n ----- Administrator Menu -----");
            System.out.println("1. Create User");
            System.out.println("2. Manage Users");
            System.out.println("3. Delete User");
            System.out.println("4. Back");
            System.out.println("Please select an option (1-4)");

            String opt = scanner.nextLine();

            if(opt == "1"){
                createUser();
            }else if(opt == "2"){
                manageUser();
            }else if(opt == "3"){
                deleteUser();
            }else if(opt == "4"){
                back=true;
            }else{
                System.out.println("Invalid input, please choose an option from 1-4");
            }
        }
    }

    private static void reviewDebrisImpact()  { System.out.println("reviewing debris impact reports.....");}
    private static void assessMissionRisk()   {System.out.println("assessing the risk levels for future missions");}
    private static void createUser()   {System.out.println("Creating a new user");}
    private static void manageUser()   {System.out.println ("Managing the users");}
    private static void deleteUser()   {System.out.println("Deleting the user");}
}
