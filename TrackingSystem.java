import java.util.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * The {@code TrackingSystem} class is responsible for managing and analyzing space objects.
 * It provides methods to track objects by type or orbit, assess debris in orbit, and generate
 * reports on orbital status and exited debris.
 *
 * <p>This class supports operations such as:
 * <ul>
 *     <li>Tracking objects in Low Earth Orbit (LEO)</li>
 *     <li>Assessing the orbital status of debris</li>
 *     <li>Writing updated orbit assessments to a CSV file</li>
 *     <li>Generating a report on debris that has exited orbit</li>
 * </ul>
 */
public class TrackingSystem {

    private List<SpaceObject> allObjects;

    /**
     * Constructs a {@code TrackingSystem} with a predefined list of space objects.
     *
     * @param objects the list of {@code SpaceObject} instances to manage
     */
    public TrackingSystem(List<SpaceObject> objects) {
        this.allObjects = objects;
    }

    /**
     * Constructs an empty {@code TrackingSystem}.
     */
    public TrackingSystem() {
        allObjects = new ArrayList<>();
    }

    /**
     * Returns the list of all space objects managed by the tracking system.
     *
     * @return the list of {@code SpaceObject} instances
     */
    public List<SpaceObject> getAllObjects() {
        return allObjects;
    }

    /**
     * Adds a new space object to the tracking system.
     *
     * @param obj the {@code SpaceObject} to add
     */
    public void addSpaceObject(SpaceObject obj) {
        allObjects.add(obj);
    }

    /**
     * Retrieves a list of space objects filtered by their type.
     *
     * @param type the type of space objects to retrieve (e.g., "Debris", "Rocket Body")
     * @return a list of {@code SpaceObject} instances matching the specified type
     */
    public List<SpaceObject> getObjectsByType(String type) {
        List<SpaceObject> result = new ArrayList<>();
        for (SpaceObject obj : allObjects) {
            if (obj.getObjectType() != null && obj.getObjectType().equalsIgnoreCase(type.trim())) {
                result.add(obj);
            }
        }
        return result;
    }

    /**
     * Retrieves a list of space objects filtered by their orbit type.
     *
     * @param orbitType the orbit type to filter by (e.g., "LEO", "GEO")
     * @return a list of {@code SpaceObject} instances matching the specified orbit type
     */
    public List<SpaceObject> getObjectsByOrbit(String orbitType) {
        List<SpaceObject> result = new ArrayList<>();
        for (SpaceObject obj : allObjects) {
            if (obj.getOrbitType() != null && obj.getOrbitType().equalsIgnoreCase(orbitType)) {
                result.add(obj);
            }
        }
        return result;
    }

    /**
     * Tracks and displays all objects currently in Low Earth Orbit (LEO).
     */
    public void trackObjectsInLEO() {
        List<SpaceObject> leoObjects = getObjectsByOrbit("LEO");
    
        //System.out.println("\n--- Objects in LEO ---");
        for (SpaceObject obj : leoObjects) {
            obj.displayInfo();
        }
    
        if (leoObjects.isEmpty()) {
            System.out.println("No objects found in LEO.");
        }
    }

    /**
     * Assesses the orbital status of debris and prints a detailed report.
     * The report includes whether debris is still in orbit, has exited orbit,
     * or has an uncertain status, along with its risk level.
     */
    public void assessDebrisInOrbit() {
        //System.out.println("\n--- Debris Orbital Assessment ---");
    
        for (SpaceObject obj : allObjects) {
            if (obj instanceof Debris) {
                boolean stillInOrbit =
                        obj.getOrbitType() != null && !obj.getOrbitType().equalsIgnoreCase("UNKNOWN") &&
                        obj.getLongitude() != 0.0 &&
                        obj.getDaysOld() < 15000 &&
                        obj.getConjunctionCount() >= 1;
    
                boolean exitedOrbit =
                        (obj.getOrbitType() == null || obj.getOrbitType().equalsIgnoreCase("UNKNOWN")) &&
                        obj.getLongitude() == 0.0 &&
                        obj.getDaysOld() > 15000 &&
                        obj.getConjunctionCount() == 0;
                
                String status;
                if (stillInOrbit) {
                    status = "Still in Orbit";
                } else if (exitedOrbit) {
                    status = "Exited Orbit";
                } else {
                    status = "Status Uncertain";
                }
    
                // Compute orbital drift
                double drift = Math.abs(obj.getLongitude() - obj.getAvgLongitude());
                String riskLevel;
                if (drift > 50) {
                    riskLevel = "High Risk";
                } else if (drift > 10) {
                    riskLevel = "Moderate Risk";
                } else {
                    riskLevel = "Low Risk";
                }
    
                // Print full assessment
                obj.displayInfo();
            }
        }
    }
    public void writeUpdatedDebrisTrackingReport(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Updated Debris Tracking Report");
            writer.println("============================\n");

            for (SpaceObject obj : allObjects) {
                if (!(obj instanceof Debris)) continue;

                boolean stillInOrbit = obj.getOrbitType() != null && !obj.getOrbitType().equalsIgnoreCase("UNKNOWN") &&
                                       obj.getLongitude() != 0.0 &&
                                       obj.getDaysOld() < 15000 &&
                                       obj.getConjunctionCount() >= 1;

                boolean exitedOrbit = (obj.getOrbitType() == null || obj.getOrbitType().equalsIgnoreCase("UNKNOWN")) &&
                                      obj.getLongitude() == 0.0 &&
                                      obj.getDaysOld() > 15000 &&
                                      obj.getConjunctionCount() == 0;

                String status = stillInOrbit ? "Still in Orbit" : exitedOrbit ? "Exited Orbit" : "Status Uncertain";

                double drift = Math.abs(obj.getLongitude() - obj.getAvgLongitude());
                String risk = drift > 50 ? "High" : drift > 10 ? "Moderate" : "Low";

                writer.printf("ID: %s\n", obj.getRecordId());
                writer.printf("Name: %s\n", obj.getSatelliteName());
                writer.printf("Orbit Type: %s\n", obj.getOrbitType());
                writer.printf("Longitude: %.2f\n", obj.getLongitude());
                writer.printf("Avg Longitude: %.2f\n", obj.getAvgLongitude());
                writer.printf("Drift: %.2f\n", drift);
                writer.printf("Risk: %s\n", risk);
                writer.printf("Status: %s\n", status);
                writer.println("----------------------------");
            }

            System.out.println("TXT debris tracking report saved to: " + filename);
        } catch (IOException e) {
            System.err.println("Failed to write updated debris tracking TXT report");
            e.printStackTrace();
        }
    }

    /**
     * Writes an updated orbit assessment to a CSV file. The CSV includes details
     * about each debris object, its orbital status, and its risk level.
     *
     * @param filename the name of the CSV file to write
     */
    public void writeUpdatedOrbitCSV(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Header row
            writer.println("record_id,satellite_name,country,orbit_type,launch_year,launch_site,longitude,avg_longitude,geohash,days_old,conjunction_count,still_in_orbit,risk_level");
    
            for (SpaceObject obj : allObjects) {
                if (!(obj instanceof Debris)) continue;
    
                boolean stillInOrbit = obj.getOrbitType() != null && !obj.getOrbitType().equalsIgnoreCase("UNKNOWN") &&
                                       obj.getLongitude() != 0.0 &&
                                       obj.getDaysOld() < 15000 &&
                                       obj.getConjunctionCount() >= 1;
    
                boolean exitedOrbit = (obj.getOrbitType() == null || obj.getOrbitType().equalsIgnoreCase("UNKNOWN")) &&
                                      obj.getLongitude() == 0.0 &&
                                      obj.getDaysOld() > 15000 &&
                                      obj.getConjunctionCount() == 0;
    
                // Compute risk level
                double drift = Math.abs(obj.getLongitude() - obj.getAvgLongitude());
                String riskLevel = (drift > 50) ? "High" : (drift > 10) ? "Moderate" : "Low";
    
                writer.printf("%s,%s,%s,%s,%d,%s,%.6f,%.6f,%s,%d,%d,%s,%s\n",
                        obj.getRecordId(), obj.getSatelliteName(), obj.getCountry(), obj.getOrbitType(),
                        obj.getLaunchYear(), obj.getLaunchSite(), obj.getLongitude(), obj.getAvgLongitude(),
                        obj.getGeohash(), obj.getDaysOld(), obj.getConjunctionCount(),
                        stillInOrbit, exitedOrbit, riskLevel);
            }
    
            System.out.println("CSV with orbit assessments saved to: " + filename);
        } catch (IOException e) {
            System.err.println("Failed to write orbit assessment CSV");
            e.printStackTrace();
        }
    }

    /**
     * Writes a report on debris that has exited orbit to a text file. The report
     * includes details about each debris object and a summary of the counts of
     * debris still in orbit and exited debris.
     *
     * @param filename the name of the text file to write
     */
    public void writeExitedDebrisReport(String filename) {
        int stillInOrbitCount = 0;
        int exitedOrbitCount = 0;
    
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Exited Debris Report");
            writer.println("====================\n");
    
            for (SpaceObject obj : allObjects) {
                if (!(obj instanceof Debris)) continue;
    
                boolean stillInOrbit = obj.getOrbitType() != null && !obj.getOrbitType().equalsIgnoreCase("UNKNOWN") &&
                                       obj.getLongitude() != 0.0 &&
                                       obj.getDaysOld() < 15000 &&
                                       obj.getConjunctionCount() >= 1;
    
                boolean exitedOrbit = (obj.getOrbitType() == null || obj.getOrbitType().equalsIgnoreCase("UNKNOWN")) &&
                                      obj.getLongitude() == 0.0 &&
                                      obj.getDaysOld() > 15000 &&
                                      obj.getConjunctionCount() == 0;
    
                if (stillInOrbit) {
                    stillInOrbitCount++;
                } else if (exitedOrbit) {
                    exitedOrbitCount++;
                    writer.printf("ID: %s | Name: %s | Country: %s | Orbit: %s | Year: %d | Site: %s | Lon: %.2f | Avg Lon: %.2f | Geohash: %s | Days Old: %d\n",
                            obj.getRecordId(), obj.getSatelliteName(), obj.getCountry(), obj.getOrbitType(),
                            obj.getLaunchYear(), obj.getLaunchSite(), obj.getLongitude(), obj.getAvgLongitude(),
                            obj.getGeohash(), obj.getDaysOld());
                }
            }
    
            writer.println("\nSummary:");
            writer.println("Still in Orbit: " + stillInOrbitCount);
            writer.println("Exited Orbit : " + exitedOrbitCount);
    
            System.out.println("TXT report on exited debris saved to: " + filename);
        } catch (IOException e) {
            System.err.println("Failed to write debris TXT report");
            e.printStackTrace();
        }
    }

/**
 * Generates and displays a density report of space objects based on user-provided longitude range.
 * <p>
 * This method prompts the user to input a minimum and maximum longitude value using the provided
 * {@code Scanner}. It then iterates through all space objects and checks if their longitude falls
 * within the specified range. For each matching object, it prints details including:
 * 
 *Record ID
 *Satellite Name
 *Country
 *Orbit Type
 *Launch Year
 *Object Type
 * 
 * At the end, it prints the total number of objects found in the given range.
 *
 *
 * If the user enters invalid input (non-numeric), an error message is displayed and the method returns.
 *
 *
 * @param scanner the {@code Scanner} object used to read user input
 */

    public void generateDensityReport(Scanner scanner) {
        System.out.println("------- Generate Density Report -------");
    
        try{
          System.out.println("Enter minimum longitude: ");
          double min = scanner.nextDouble();
          System.out.println("Enter maximum longitude");
          double max = scanner.nextDouble();
          scanner.nextLine();
          int count = 0;
    
          for(SpaceObject obj : allObjects){
              double longitude = obj.getLongitude();
              if(longitude >= min && longitude <= max){
                  System.out.println("Record ID: " + obj.getRecordId());
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
        }catch (InputMismatchException e){
          System.out.println("Invalid input, please enter valid numbers.");
          scanner.nextLine();
        }
    }

/**
 * Analyzes and displays long-term impact information for space objects in Low Earth Orbit (LEO).
 * 
 * The method iterates through all space objects and filters those that:
 * 
 * Are in LEO (case-insensitive)
 *Have a {@code daysOld} value greater than 200
 *Have a {@code conjunctionCount} greater than 0
 *
 * For each qualifying object, it prints relevant details including:
 * Record ID, Satellite Name, Country, Orbit Type, Object Type,
 * Days Old, and Conjunction Count.
 * 
 * 
 * 
 * Example output:
 * Record ID: 12345
 * Satellite Name: Sentinel
 * Country: USA
 * Orbit Type: LEO
 * Object Type: Satellite
 * Days Old: 450
 * Conjunction Count: 3
 *
 */

    public void analyzeLongTermImpact(){
        System.out.println("------- Long Term Impact Analysis -------");
        for(SpaceObject obj : allObjects){
            if(obj.getOrbitType() != null && obj.getOrbitType().equalsIgnoreCase("LEO") && obj.getDaysOld() >= 200 && obj.getConjunctionCount() >0){
                obj.displayInfo();
            } 
        }
    }
}
