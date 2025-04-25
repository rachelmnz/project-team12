import java.util.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class TrackingSystem {
    private List<SpaceObject> allObjects;

    public TrackingSystem(List<SpaceObject> objects) {
        this.allObjects = objects;
    }

    public List<SpaceObject> getAllObjects() {
        return allObjects;
    }

    public TrackingSystem() {
        allObjects = new ArrayList<>();
    }

    public void addSpaceObject(SpaceObject obj) {
        allObjects.add(obj);
    }

    public List<SpaceObject> getObjectsByType(String type) {
        List<SpaceObject> result = new ArrayList<>();
        for (SpaceObject obj : allObjects) {
            if (obj.getObjectType() != null && obj.getObjectType().equalsIgnoreCase(type.trim())) {
                result.add(obj);
            }
        }
        return result;
    }

    public List<SpaceObject> getObjectsByOrbit(String orbitType) {
        List<SpaceObject> result = new ArrayList<>();
        for (SpaceObject obj : allObjects) {
            if (obj.getOrbitType() != null && obj.getOrbitType().equalsIgnoreCase(orbitType)) {
                result.add(obj);
            }
        }
        return result;
    }    

    public void trackObjectsInLEO() {
        List<SpaceObject> leoObjects = getObjectsByOrbit("LEO");
    
        System.out.println("\n--- Objects in LEO ---");
        for (SpaceObject obj : leoObjects) {
            System.out.println("Record ID: " + obj.getRecordId() +
                               ", Name: " + obj.getSatelliteName() +
                               ", Country: " + obj.getCountry() +
                               ", Orbit Type: " + obj.getOrbitType() +
                               ", Launch Year: " + obj.getLaunchYear() +
                               ", Launch Site: " + obj.getLaunchSite() +
                               ", Longitude: " + obj.getLongitude() +
                               ", Avg. Longitude: " + obj.getAvgLongitude() +
                               ", Geohash: " + obj.getGeohash() +
                               ", Days Old: " + obj.getDaysOld());
        }
    
        if (leoObjects.isEmpty()) {
            System.out.println("No objects found in LEO.");
        }
    }

    public void assessDebrisInOrbit() {
        System.out.println("\n--- Debris Orbital Assessment ---");
    
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
                System.out.println("ID: " + obj.getRecordId() +
                        ", Name: " + obj.getSatelliteName() +
                        ", Status: " + status +
                        ", Risk Level: " + riskLevel +
                        ", Orbit Type: " + obj.getOrbitType() +
                        ", Drift: " + drift);
            }
        }
    }

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
                        stillInOrbit, riskLevel);
            }
    
            System.out.println("CSV with orbit assessments saved to: " + filename);
        } catch (IOException e) {
            System.err.println("Failed to write orbit assessment CSV");
            e.printStackTrace();
        }
    }

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
    
    
}
