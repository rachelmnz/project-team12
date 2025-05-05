import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
* Utility class for logging messages with timestamps and saving them to a file.
*/

public class LoggerUtil {
/** Stores log entries in memory before saving to file. */
    private static final List<String> logEntries = new ArrayList<>();
/** Default log file name. */
    private static final String LOG_FILE = "log.txt";
/**
* Logs a message with the current timestamp.
*
* @param message the message to log
*/

    public static void log(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logMessage = "[" + timestamp + "] " + message;
        logEntries.add(logMessage);
        System.out.println(logMessage); // Optional: echo to console
    }
/**
* Saves all logged messages to the specified file and clears the log buffer.
*
* @param filename the name of the file to write to
*/

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
/**
* Saves the log to the default log file.
*/

    public static void saveLatestLog() {
        saveLog(LOG_FILE);
    }
}
