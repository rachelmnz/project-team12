import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
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
   
    public static List<SpaceObject> loadSpaceObjects(String filePath) {
        List<SpaceObject> objects = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine(); // read header
            if (headerLine == null) return objects;

            String[] headers = headerLine.split(",", -1);
            Map<String, Integer> headerMap = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                headerMap.put(headers[i].trim().toLowerCase(), i); // lowercase for robustness
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1); // include empty trailing fields

                try {
                    String recordId = getSafe(data, headerMap, "record_id");
                    String satelliteName = getSafe(data, headerMap, "satellite_name");
                    String country = getSafe(data, headerMap, "country");
                    String orbitType = getSafe(data, headerMap, "approximate_orbit_type");
                    String launchSite = getSafe(data, headerMap, "launch_site");
                    double longitude = parseDoubleSafe(getSafe(data, headerMap, "longitude"));
                    double avgLongitude = parseDoubleSafe(getSafe(data, headerMap, "avg_longitude"));
                    String geohash = getSafe(data, headerMap, "geohash");
                    int launchYear = parseIntSafe(getSafe(data, headerMap, "launch_year"));
                    int daysOld = parseIntSafe(getSafe(data, headerMap, "days_old"));
                    int conjunctionCount = parseIntSafe(getSafe(data, headerMap, "conjunction_count"));

                    // Infer object type from name
                    String nameUpper = satelliteName.toUpperCase();
                    String inferredType = "UNKNOWN";
                    if (nameUpper.contains("DEB")) {
                        inferredType = "DEBRIS";
                    } else if (nameUpper.contains("R/B") || nameUpper.contains("ROCKET")) {
                        inferredType = "ROCKET BODY";
                    } else if (nameUpper.contains("SAT") || nameUpper.contains("STARLINK") || nameUpper.contains("PAYLOAD")) {
                        inferredType = "PAYLOAD";
                    }

                    SpaceObject obj = switch (inferredType) {
                        case "DEBRIS" -> new Debris(recordId, satelliteName, country, orbitType, launchYear,
                                launchSite, longitude, avgLongitude, geohash, daysOld, conjunctionCount);
                        case "ROCKET BODY" -> new RocketBody(recordId, satelliteName, country, orbitType, launchYear,
                                launchSite, longitude, avgLongitude, geohash, daysOld, conjunctionCount);
                        case "PAYLOAD" -> new Payload(recordId, satelliteName, country, orbitType, launchYear,
                                launchSite, longitude, avgLongitude, geohash, daysOld, conjunctionCount);
                        default -> new UnknownObject(recordId, satelliteName, country, orbitType, launchYear,
                                launchSite, longitude, avgLongitude, geohash, daysOld, conjunctionCount);
                    };

                    objects.add(obj);
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return objects;
    }

    private static String getSafe(String[] data, Map<String, Integer> headerMap, String key) {
        Integer idx = headerMap.get(key.toLowerCase());
        if (idx != null && idx < data.length) {
            String value = data[idx].trim();
            //System.out.println("DEBUG: Key: " + key + ", Value: [" + value + "]");
            return value;
        }
        return "";
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