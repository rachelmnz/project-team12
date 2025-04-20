import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LoggerUtil.java
 * Utility class for writing log entries to a file.
 * Appends logs to 'log.txt' and includes timestamped entries.
 */
public class LoggerUtil {

    private static final String LOG_FILE = "log.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void log(String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String entry = String.format("[%s] %s%n", timestamp, message);

        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(entry);
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to write to log file: " + e.getMessage());
        }
    }
} 
