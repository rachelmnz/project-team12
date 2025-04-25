/**
 * The {@code SpaceObject} class represents a generic object in space, such as a satellite,
 * debris, or rocket body. It contains common attributes and methods shared by all space objects.
 *
 * <p>This class serves as a base class for more specific types of space objects, such as
 * {@code Debris}, {@code RocketBody}, and {@code Payload}.
 */
//abstract-------------------------------------------------------------------------------------------
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
     * Constructs a {@code SpaceObject} with the specified attributes.
     *
     * @param recordId the unique record ID of the space object
     * @param satelliteName the name of the satellite or object
     * @param country the country associated with the space object
     * @param orbitType the type of orbit (e.g., LEO, GEO)
     * @param launchYear the year the object was launched
     * @param launchSite the site where the object was launched
     * @param longitude the current longitude of the object
     * @param avgLongitude the average longitude of the object
     * @param geohash the geohash representing the object's location
     * @param daysOld the number of days since the object was launched
     * @param conjunctionCount the number of conjunction events associated with the object
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
     * Displays the information of the space object in a formatted table row.
     */
    public void displayInfo() {
        System.out.printf(
            "%-8s | %-25s | %-10s | %-6s | %-6d | %-8s | %-10.2f | %-12.2f | %-12s | %-9d | %-14d\n",
            recordId, satelliteName, country, orbitType, launchYear,
            launchSite, longitude, avgLongitude, geohash, daysOld, conjunctionCount
        );
    }
}
