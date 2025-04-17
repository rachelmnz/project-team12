import java.io.*;
import java.util.*;

public class FileHandler {
    private TrackingSystem trackingSystem;

    public FileHandler(TrackingSystem trackingSystem) {
        this.trackingSystem = trackingSystem;
    }

    public void loadData(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String header = br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String recordId = tokens[0].trim();
                String satelliteName = tokens[1].trim();
                String country = tokens[2].trim();
                String orbitType = tokens[3].trim();
                int launchYear = Integer.parseInt(tokens[4].trim());
                String launchSite = tokens[5].trim();
                double longitude = Double.parseDouble(tokens[6].trim());
                double avgLongitude = Double.parseDouble(tokens[7].trim());
                String geohash = tokens[8].trim();
                int daysOld = Integer.parseInt(tokens[9].trim());
                int conjunctionCount = Integer.parseInt(tokens[10].trim());

                SpaceObject obj;
                if (satelliteName.toLowerCase().contains("debris")) {
                    obj = new Debris(recordId, satelliteName, country, orbitType, launchYear, launchSite,
                                     longitude, avgLongitude, geohash, daysOld, conjunctionCount);
                } else {
                    obj = new Satellite(recordId, satelliteName, country, orbitType, launchYear, launchSite,
                                        longitude, avgLongitude, geohash, daysOld, conjunctionCount);
                }
                trackingSystem.addSpaceObject(obj);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
