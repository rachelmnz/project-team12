/**
 * Represents a rocket body object in orbit, typically from a launch vehicle.
 * Inherits from {@code SpaceObject}.
 */
public class RocketBody extends SpaceObject {
 /**
* Constructs a RocketBody object with the given attributes and sets its type.
*
* @param recordId          the record ID of the object
* @param satelliteName     the satellite name (if any)
* @param country           the country of origin
* @param orbitType         the orbit type (e.g., LEO, GEO)
* @param launchYear        the year the object was launched
* @param launchSite        the launch site of the object
* @param longitude         the current longitude
* @param avgLongitude      the average longitude
* @param geohash           the geohash representing the object's location
* @param daysOld           number of days since the object was launched
* @param conjunctionCount  number of recorded conjunction events
*/
    public RocketBody(String recordId, String satelliteName, String country, String orbitType,
                      int launchYear, String launchSite, double longitude, double avgLongitude,
                      String geohash, int daysOld, int conjunctionCount) {
        super(recordId, satelliteName, country, orbitType, launchYear, launchSite,
              longitude, avgLongitude, geohash, daysOld, conjunctionCount);
        setObjectType("ROCKET BODY");
    }
}
