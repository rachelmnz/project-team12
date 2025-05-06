/**
 * Represents a satellite object in orbit.
 * Inherits from {@code SpaceObject}.
 */
public class Satellite extends SpaceObject {

/**
* Constructs a {@code Satellite} object with the specified attributes.
*
* @param recordId          the record ID of the object
* @param satelliteName     the name of the satellite
* @param country           the country of origin
* @param orbitType         the orbit type (e.g., LEO, MEO, GEO)
* @param launchYear        the year the object was launched
* @param launchSite        the launch site
* @param longitude         the current longitude
* @param avgLongitude      the average longitude
* @param geohash           the geohash representing the location
* @param daysOld           number of days since launch
* @param conjunctionCount  number of recorded conjunction events
*/
    public Satellite(String recordId, String satelliteName, String country, String orbitType,     
                     int launchYear, String launchSite, double longitude, double avgLongitude,
                     String geohash, int daysOld, int conjunctionCount) {
        super(recordId, satelliteName, country, orbitType, launchYear, launchSite,
              longitude, avgLongitude, geohash, daysOld, conjunctionCount);
    }
}
