public class Debris extends SpaceObject {

    public Debris(String recordId, String satelliteName, String country, String orbitType,
                  int launchYear, String launchSite, double longitude, double avgLongitude,
                  String geohash, int daysOld, int conjunctionCount) {
        super(recordId, satelliteName, country, orbitType, launchYear, launchSite,
              longitude, avgLongitude, geohash, daysOld, conjunctionCount);
    }

    public void displayInfo() {
        System.out.println("Debris ID: " + recordId + ", Name: " + satelliteName + ", Country: " + country);
    }
}

