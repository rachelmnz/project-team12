import java.util.*;

public class UserManager {
    private final Map<String, User> users = new HashMap<>();

    public boolean createUser(String username, String password, String role) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists.");
            return false;
        }
        users.put(username, new User(username, password, role.toUpperCase()));
        System.out.println("User created successfully.");
        return true;
    }

    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public void printAllUsers() {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        System.out.println("\n--- Registered Users ---");
        users.values().forEach(user ->
            System.out.printf("Username: %s | Role: %s\n", user.getUsername(), user.getRole()));
    }

    public boolean updateUser(String oldUsername, String newUsername, String newPassword) {
        User user = users.get(oldUsername);
        if (user == null) {
            System.out.println("User not found.");
            return false;
        }
        users.remove(oldUsername);
        users.put(newUsername, new User(newUsername, newPassword, user.getRole()));
        System.out.println("User updated successfully.");
        return true;
    }

    public boolean deleteUser(String username) {
        if (users.containsKey(username)) {
            users.remove(username);
            System.out.println("User deleted successfully.");
            return true;
        } else {
            System.out.println("User not found.");
            return false;
        }
    }

    public static class User {
        private final String username;
        private final String password;
        private final String role;

        public User(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }
    }
}
