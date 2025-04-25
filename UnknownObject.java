public class UnknownObject extends SpaceObject {

    public UnknownObject(String recordId, String satelliteName, String country, String orbitType,
                         int launchYear, String launchSite, double longitude, double avgLongitude,
                         String geohash, int daysOld, int conjunctionCount) {
        super(recordId, satelliteName, country, orbitType, launchYear, launchSite,
              longitude, avgLongitude, geohash, daysOld, conjunctionCount);
        setObjectType("UNKNOWN");
    }

    // No need to add getters and setters; they are inherited from SpaceObject.
}