import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {
    public static List<SpaceObject> loadSpaceObjects(String filePath) {
        List<SpaceObject> objects = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1); // -1 to include trailing empty fields

                if (data.length >= 20) {
                    try {
                        String recordId = data[0];
                        String satelliteName = data[2];
                        String country = data[3];
                        String orbitType = data[4];
                        String objectType = data[5];
                        int launchYear = parseIntSafe(data[6]);
                        String launchSite = data[7];
                        double longitude = parseDoubleSafe(data[8]);
                        double avgLongitude = parseDoubleSafe(data[9]);
                        String geohash = data[10];
                        int daysOld = parseIntSafe(data[18]);
                        int conjunctionCount = parseIntSafe(data[19]);

                        SpaceObject obj = null;

                        switch (objectType.toUpperCase()) {
                            case "DEBRIS":
                                obj = new Debris(recordId, satelliteName, country, orbitType, launchYear,
                                                 launchSite, longitude, avgLongitude, geohash, daysOld, conjunctionCount);
                                break;
                            case "ROCKET BODY":
                                //obj = new RocketBody(recordId, satelliteName, country, orbitType, launchYear,
                                //                     launchSite, longitude, avgLongitude, geohash, daysOld, conjunctionCount);
                                break;
                            case "PAYLOAD":
                                //obj = new Payload(recordId, satelliteName, country, orbitType, launchYear,
                                //                  launchSite, longitude, avgLongitude, geohash, daysOld, conjunctionCount);
                                break;
                            default:
                                //obj = new UnknownObject(recordId, satelliteName, country, orbitType, launchYear,
                                //                        launchSite, longitude, avgLongitude, geohash, daysOld, conjunctionCount);
                        }

                        if (obj != null) {
                            objects.add(obj);
                        }
                    } catch (Exception e) {
                        System.err.println("Error parsing line: " + line);
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return objects;
    }

    private static int parseIntSafe(String str) {
        try {
            return Integer.parseInt(str.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private static double parseDoubleSafe(String str) {
        try {
            return Double.parseDouble(str.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }
}

