/**
 * The class represents a specific type of space object: debris.
 * It extends the class and initializes its fields using
 * the constructor parameters. The object type is explicitly set to "DEBRIS".
 * 
 * This class is part of the space debris tracking system, used to model
 * and interact with debris objects in Low Earth Orbit (LEO).</p>
 */ 


public class Debris extends SpaceObject {

    public Debris(String recordId, String satelliteName, String country, String orbitType,
                  int launchYear, String launchSite, double longitude, double avgLongitude,
                  String geohash, int daysOld, int conjunctionCount) {
        super(recordId, satelliteName, country, orbitType, launchYear, launchSite,
              longitude, avgLongitude, geohash, daysOld, conjunctionCount);
        setObjectType("DEBRIS");
    }
    // No need to add getters and setters; they are inherited from SpaceObject.
}

