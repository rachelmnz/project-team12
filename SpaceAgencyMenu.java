import java.util.List;
import java.util.Scanner;

public class SpaceAgencyMenu{
  private final TrackingSystem tracker;
  private final Scanner scanner;

  public SpaceAgencyMenu(TrackingSystem tracker, Scanner scanner){
    this.tracker = tracker;
    this.scanner = scanner;
  }

  public void showMenu(){
    boolean exit = false;
    while(!exit){
      System.out.println("\n-- Space Agency Representative Menu --");
      System.out.println("1. Analyze Long Term Impact");
      System.out.println("2. Generate Density Reports");
      System.out.println("3. Return to Main Menu");
      System.out.print("Select an option: ");
      String input = scanner.nextLine();

      if(choice.equals("1")){
        analyzeLongTermImpact();
      }else if(choice.equals("2")){
        generateDensityReport();
      }else if (choice.equals("3")){
        exit = true;
      }else{
        System.out.println("Invalid choice please try again");
      }
    }
  }

  private void analyzeLongTermImpact(){
    List<SpaceObject> allObjects = tracker.getAllObjects();

    System.out.println("------- Long Term Impact Analysis -------");
    for(int i =0; i<allObjects.size(); i++){
      SpaceObject obj = allObjects.get(i);

      if(obj.getOrbitTye().equalsIgnoreCase("LEO") && obj.getDaysOld()>200 && obj.getConjunctionCount()>0){
         System.out.println("Record ID: " + obj.getRecordID());
         System.out.println("Satellite Name: " + obj.getSatelliteName());
         System.out.println("Country: " + obj.getCountry());
         System.out.println("Orbit Type: " + obj.getOrbitType());
         System.out.println("Object Type: " + obj.getObjectType());
         System.out.println("Days Old: " + obj.getDaysOld());
         System.out.println("Conjunction Count: " + obj.getConjunctionCount());
         System.out.println("-----------------------------");
      }
    }
  }

  private void generateDensityReport(){
    System.out.println("------- Generate Density Report -------");

    try{
      System.out.println("Enter minimum longitude: ");
      double min = scanner.nextDouble();
      System.out.println("Enter maximum longitude");
      double max = scanner.nextDouble();
      scanner.nextLine();

      List<SpaceOBject> allObjects = trakcer.getAllObjects();
      int count = 0;

      for(int i =0; i< allObjects.size(); i++){
        SpaceObject obj = allObjects.get(i);
        double long = obj.getLongitude();

        if(lon >= min && lon <= max){
          System.out.println("Record ID: " + obj.getRecordID());
          System.out.println("Satellite Name: " + obj.getSatelliteName());
          System.out.println("Country: " + obj.getCountry());
          System.out.println("Orbit Type: " + obj.getOrbitType());
          System.out.println("Launch Year: " + obj.getLaunchYear());
          System.out.println("Object Type: " + obj.getObjectType());
          System.out.println("-----------------------------");
          count++;
        }
      }
      System.out.println("Total objects that were found in range:" + count);
    }catch (Exception e){
      System.out.println("Invalid input, please enter valid numbers.");
      scanner.nextLine();
    }
  }
}
