import java.util.Scanner;

/**
 * Abstract base class representing a user in the system.
 * Subclasses must implement a role-specific menu through {@code displayMenu()}.
 */

abstract class User {
    protected Scanner scanner;
    protected TrackingSystem tracker;

    public User(Scanner scanner, TrackingSystem tracker) {
        this.scanner = scanner;
        this.tracker = tracker;
    }

    public abstract void displayMenu();
}
