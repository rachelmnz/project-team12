import java.util.*;
import java.io.*;

/**
 * This manages user accounts including creation, authentication, updates, deletion,
 * and persistence through a CSV file.
 */

public class UserManager {
    private final Map<String, User> users = new HashMap<>();
    private final String csvFile = "users.csv";
/**
* Constructs a UserManager and loads users from a CSV file.
* If no users are found, a default admin account is created.
*/
    public UserManager() {
        loadUsersFromCSV();
        if (users.isEmpty()) {
            System.out.println("No users found. Creating default admin account.");
            createUser("admin", "admin123", "Administrator");
        }
    }
/**
* Creates a new user if the username does not already exist.
*
* @param username the new user's username
* @param password the new user's password
* @param role     the role of the new user
* @return true if user is successfully created, false otherwise
*/

    public boolean createUser(String username, String password, String role) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists.");
            return false;
        }
        User user = new User(username, password, role.toUpperCase());
        users.put(username, user);
        saveUsersToCSV();
        System.out.println("User created successfully.");
        return true;
    }
/**
* Authenticates a user by verifying username and password.
*
* @param username the input username
* @param password the input password
* @return the User if credentials are valid, null otherwise
*/

    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
/**
* Retrieves a user by their username.
*
* @param username the username to search
* @return the User if found, null otherwise
*/
    public User getUser(String username) {
        return users.get(username);
    }

/**
* Prints all registered users to the console.
*/

    public void printAllUsers() {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        System.out.println("\n--- Registered Users ---");
        users.values().forEach(user ->
            System.out.printf("Username: %s | Role: %s\n", user.getUsername(), user.getRole()));
    }
    
/**
* Updates an existing user's username and password.
*
* @param oldUsername the current username
* @param newUsername the new username
* @param newPassword the new password
* @return true if update was successful, false if user not found
*/
    public boolean updateUser(String oldUsername, String newUsername, String newPassword) {
        User user = users.get(oldUsername);
        if (user == null) {
            System.out.println("User not found.");
            return false;
        }
        users.remove(oldUsername);
        users.put(newUsername, new User(newUsername, newPassword, user.getRole()));
        saveUsersToCSV();
        System.out.println("User updated successfully.");
        return true;
    }

/**
* Deletes a user by username.
*
* @param username the username of the user to delete
* @return true if deleted successfully, false if user not found
*/

    public boolean deleteUser(String username) {
        if (users.containsKey(username)) {
            users.remove(username);
            saveUsersToCSV();
            System.out.println("User deleted successfully.");
            return true;
        } else {
            System.out.println("User not found.");
            return false;
        }
    }

/**
* Loads users from a CSV file into the users map.
*/
    private void loadUsersFromCSV() {
        File file = new File(csvFile);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    String role = parts[2].trim();
                    users.put(username, new User(username, password, role));
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load users from CSV.");
            e.printStackTrace();
        }
    }

/**
* Saves all users to the CSV file.
*/
    private void saveUsersToCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            for (User user : users.values()) {
                writer.printf("%s,%s,%s\n", user.getUsername(), user.getPassword(), user.getRole());
            }
        } catch (IOException e) {
            System.err.println("Failed to save users to CSV.");
            e.printStackTrace();
        }
    }

/**
* Represents a user with a username, password, and role.
*/
    public static class User {
        private final String username;
        private final String password;
        private final String role;

/**
* Constructs a User object.
*
* @param username the username
* @param password the password
* @param role     the role (e.g., Scientist, Administrator)
*/
        public User(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }
/** @retun the username*/
        public String getUsername() {
            return username;
        }
/**@return the password*/
        public String getPassword() {
            return password;
        }
/**@return the role*/
        public String getRole() {
            return role;
        }
    }
}
