/**
 * Represents a space object with an unknown classification.
 * Inherits properties from {@code SpaceObject} and sets the type to "UNKNOWN".
 */
public class UnknownObject extends SpaceObject {

 /**
* Constructs an {@code UnknownObject} with the specified attributes.
*
* @param recordId          unique record ID
* @param satelliteName     name of the object
* @param country           country of origin
* @param orbitType         type of orbit (e.g., LEO, GEO)
* @param launchYear        year the object was launched
* @param launchSite        launch site location
* @param longitude         current longitude of the object
* @param avgLongitude      average longitude over time
* @param geohash           geohash location code
* @param daysOld           number of days since launch
* @param conjunctionCount  number of conjunction events
*/

    public UnknownObject(String recordId, String satelliteName, String country, String orbitType,
                         int launchYear, String launchSite, double longitude, double avgLongitude,
                         String geohash, int daysOld, int conjunctionCount) {
        super(recordId, satelliteName, country, orbitType, launchYear, launchSite,
              longitude, avgLongitude, geohash, daysOld, conjunctionCount);
        setObjectType("UNKNOWN");
    }

    // No need to add getters and setters; they are inherited from SpaceObject.
}
