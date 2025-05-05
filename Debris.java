/**
 * The debris class represents a spece object that is a debris, 
 * This is initializing the debirs object with the parameters and objectifying it
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

