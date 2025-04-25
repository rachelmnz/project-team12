import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The {@code FileHandler} class is responsible for loading space object data from a CSV file
 * and creating corresponding {@code SpaceObject} instances. It supports parsing and handling
 * different types of space objects, such as {@code Debris}, {@code RocketBody}, {@code Payload},
 * and {@code UnknownObject}.
 *
 * <p>This class provides utility methods to safely parse integers and doubles from strings
 * to handle potential parsing errors gracefully.
 */
public class FileHandler {

    /**
     * Loads space objects from a CSV file and returns a list of {@code SpaceObject} instances.
     *
     * @param filePath the path to the CSV file containing space object data
     * @return a list of {@code SpaceObject} instances
     */
    //fix -> hash map!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
                        String launchSite = data[7];
                        double longitude = parseDoubleSafe(data[8]);
                        double avgLongitude = parseDoubleSafe(data[9]);
                        String geohash = data[10];
                        int launchYear = parseIntSafe(data[6]);
                        int daysOld = parseIntSafe(data[18]);
                        int conjunctionCount = parseIntSafe(data[19]);

                        // Infer object type from satellite name
                        String nameUpper = satelliteName.toUpperCase();
                        String inferredType = "UNKNOWN";

                        if (nameUpper.contains("DEB")) {
                            inferredType = "DEBRIS";
                        } else if (nameUpper.contains("R/B") || nameUpper.contains("ROCKET")) {
                            inferredType = "ROCKET BODY";
                        } else if (nameUpper.contains("SAT") || nameUpper.contains("STARLINK") || nameUpper.contains("PAYLOAD")) {
                            inferredType = "PAYLOAD";
                        }

                        SpaceObject obj = null;

                        switch (inferredType) {
                            case "DEBRIS":
                                obj = new Debris(recordId, satelliteName, country, orbitType, launchYear,
                                        launchSite, longitude, avgLongitude, geohash, daysOld, conjunctionCount);
                                break;
                            case "ROCKET BODY":
                                obj = new RocketBody(recordId, satelliteName, country, orbitType, launchYear,
                                        launchSite, longitude, avgLongitude, geohash, daysOld, conjunctionCount);
                                break;
                            case "PAYLOAD":
                                obj = new Payload(recordId, satelliteName, country, orbitType, launchYear,
                                        launchSite, longitude, avgLongitude, geohash, daysOld, conjunctionCount);
                                break;
                            case "UNKNOWN":
                                obj = new UnknownObject(recordId, satelliteName, country, orbitType, launchYear,
                                        launchSite, longitude, avgLongitude, geohash, daysOld, conjunctionCount);
                                break;
                            default:
                                break; // Should not reach here
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

    /**
     * Safely parses an integer from a string, returning 0 if parsing fails.
     *
     * @param str the string to parse
     * @return the parsed integer or 0 if parsing fails
     */
    private static int parseIntSafe(String str) {
        try {
            return Integer.parseInt(str.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Safely parses a double from a string, returning 0.0 if parsing fails.
     *
     * @param str the string to parse
     * @return the parsed double or 0.0 if parsing fails
     */
    private static double parseDoubleSafe(String str) {
        try {
            return Double.parseDouble(str.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }
}
