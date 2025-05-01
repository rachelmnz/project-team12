import java.util.Scanner;

abstract class User {
    protected Scanner scanner;
    protected TrackingSystem tracker;

    public User(Scanner scanner, TrackingSystem tracker) {
        this.scanner = scanner;
        this.tracker = tracker;
    }

    public abstract void displayMenu();
}
