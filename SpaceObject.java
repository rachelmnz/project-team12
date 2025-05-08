/**
 * The {@code SpaceObject} class represents a generic object in space, such as a satellite,
 * debris, or rocket body. It contains common attributes and methods shared by all space objects.
 */
public class SpaceObject {
    protected String recordId;
    protected String satelliteName;
    protected String country;
    protected String orbitType;
    protected String objectType;
    protected int launchYear;
    protected String launchSite;
    protected double longitude;
    protected double avgLongitude;
    protected String geohash;
    protected int daysOld;
    protected int conjunctionCount;
/**
* Constructs a {@code SpaceObject} with the specified parameters.
*
* @param recordId          unique record identifier
* @param satelliteName     name of the satellite
* @param country           country of origin
* @param orbitType         type of orbit
* @param launchYear        year of launch
* @param launchSite        site of launch
* @param longitude         current longitude
* @param avgLongitude      average longitude
* @param geohash           location encoded as geohash
* @param daysOld           age of the object in days
* @param conjunctionCount  number of conjunction events
*/

    public SpaceObject(String recordId, String satelliteName, String country, String orbitType, 
                       int launchYear, String launchSite, double longitude, double avgLongitude,
                       String geohash, int daysOld, int conjunctionCount) {
        this.recordId = recordId;
        this.satelliteName = satelliteName;
        this.country = country;
        this.orbitType = orbitType;
        this.launchYear = launchYear;
        this.launchSite = launchSite;
        this.longitude = longitude;
        this.avgLongitude = avgLongitude;
        this.geohash = geohash;
        this.daysOld = daysOld;
        this.conjunctionCount = conjunctionCount;
    }
/**
* Displays formatted details of the space object.
*/

    public void displayInfo() {
        System.out.printf(
            "%-8s | %-25s | %-10s | %-6s | %-6d | %-8s | %-10.2f | %-12.2f | %-12s | %-9d | %-14d\n",
            recordId, satelliteName, country, orbitType, launchYear,
            launchSite, longitude, avgLongitude, geohash, daysOld, conjunctionCount
        );
    }

    

    // Getters
    public String getRecordId() { return recordId; }
    public String getSatelliteName() { return satelliteName; }
    public String getCountry() { return country; }
    public String getOrbitType() { return orbitType; }
    public String getObjectType() { return objectType; }
    public int getLaunchYear() { return launchYear; }
    public String getLaunchSite() { return launchSite; }
    public double getLongitude() { return longitude; }
    public double getAvgLongitude() { return avgLongitude; }
    public String getGeohash() { return geohash; }
    public int getDaysOld() { return daysOld; }
    public int getConjunctionCount() { return conjunctionCount; }

    // Setters
    public void setObjectType(String objectType) { this.objectType = objectType; }
}
