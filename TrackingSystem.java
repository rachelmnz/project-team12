import java.util.*;

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
    

    public void writeUpdatedReport(String a){
        return;
    }

    public void trackObjectsInSpace(){
        //
        return;
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
    
}
