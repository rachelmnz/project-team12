import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LoggerUtil {
    private static final List<String> logEntries = new ArrayList<>();
    private static final String LOG_FILE = "log.txt";

    public static void log(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logMessage = "[" + timestamp + "] " + message;
        logEntries.add(logMessage);
        System.out.println(logMessage); // Optional: echo to console
    }

    public static void saveLog(String filename) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            for (String entry : logEntries) {
                writer.write(entry + System.lineSeparator());
            }
            logEntries.clear(); // Clear after saving
            System.out.println("Log saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving log: " + e.getMessage());
        }
    }

    public static void saveLatestLog() {
        saveLog(LOG_FILE);
    }
}
