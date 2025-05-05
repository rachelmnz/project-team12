import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {

    public static Map<String, SpaceObject> loadSpaceObjects(String filePath) {
        Map<String, SpaceObject> objects = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine();
            if (headerLine == null) return objects;

            String[] headers = headerLine.split(",", -1);
            Map<String, Integer> columnIndex = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                columnIndex.put(headers[i].trim().toLowerCase(), i);
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                try {
                    String recordId = getValue(data, columnIndex, "recordid");
                    String satelliteName = getValue(data, columnIndex, "satellitename");
                    String country = getValue(data, columnIndex, "country");
                    String orbitType = getValue(data, columnIndex, "orbittype");
                    String launchSite = getValue(data, columnIndex, "launchsite");
                    double longitude = parseDoubleSafe(getValue(data, columnIndex, "longitude"));
                    double avgLongitude = parseDoubleSafe(getValue(data, columnIndex, "avglongitude"));
                    String geohash = getValue(data, columnIndex, "geohash");
                    int launchYear = parseIntSafe(getValue(data, columnIndex, "launchyear"));
                    int daysOld = parseIntSafe(getValue(data, columnIndex, "daysold"));
                    int conjunctionCount = parseIntSafe(getValue(data, columnIndex, "conjunctioncount"));

                    String inferredType = inferObjectType(satelliteName);

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

                    objects.put(recordId, obj);

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

    private static String getValue(String[] data, Map<String, Integer> indexMap, String columnName) {
        Integer index = indexMap.get(columnName.toLowerCase());
        return (index != null && index < data.length) ? data[index] : "";
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

    private static String inferObjectType(String name) {
        String nameUpper = name.toUpperCase();
        if (nameUpper.contains("DEB")) return "DEBRIS";
        if (nameUpper.contains("R/B") || nameUpper.contains("ROCKET")) return "ROCKET BODY";
        if (nameUpper.contains("SAT") || nameUpper.contains("STARLINK") || nameUpper.contains("PAYLOAD")) return "PAYLOAD";
        return "UNKNOWN";
    }
}

