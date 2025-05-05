/**
 * Represents a payload object in orbit, such as an active or inactive satellite.
 * Inherits from {@code SpaceObject}.
 */

public class Payload extends SpaceObject {
/**
*Constructs a payload object with the given parameters and sets its type
* @param recordId          the record ID of the object
* @param satelliteName     the satellite name
* @param country           the country of origin
* @param orbitType         the orbit type (e.g., LEO, MEO)
* @param launchYear        the year the object was launched
* @param launchSite        the launch site of the object
* @param longitude         the current longitude of the object
* @param avgLongitude      the average longitude over time
* @param geohash           the geohash location code
* @param daysOld           number of days since launch
* @param conjunctionCount  number of recorded conjunctions
*/

    public Payload(String recordId, String satelliteName, String country, String orbitType,
                   int launchYear, String launchSite, double longitude, double avgLongitude,
                   String geohash, int daysOld, int conjunctionCount) {
        super(recordId, satelliteName, country, orbitType, launchYear, launchSite,
              longitude, avgLongitude, geohash, daysOld, conjunctionCount);
        setObjectType("PAYLOAD");
    }

    // No need to add getters and setters; they are inherited from SpaceObject.
}
