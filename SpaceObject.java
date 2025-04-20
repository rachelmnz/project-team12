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

    public void displayInfo() {
        System.out.println(
            "ID: " + recordId + ", Name: " + satelliteName +
            ", Country: " + country + ", Orbit: " + orbitType +
            ", Year: " + launchYear + ", Site: " + launchSite +
            ", Lon: " + longitude + ", Avg Lon: " + avgLongitude +
            ", Geohash: " + geohash + ", Days Old: " + daysOld +
            ", Conjunctions: " + conjunctionCount
        );
    }
    
}
