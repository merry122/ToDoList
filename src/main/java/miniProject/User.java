package miniProject;
/**
 * User - Represents a user in the system.
 *
 * This class holds information about a user including their username, email, and password.
 * It provides getter and setter methods to access and modify user details.
 *
 * Methods:
 * - `getUsername()`: Returns the username of the user.
 * - `setUsername(String username)`: Sets the username of the user.
 * - `getEmail()`: Returns the email of the user.
 * - `setEmail(String email)`: Sets the email of the user.
 * - `getPassword()`: Returns the password of the user.
 * - `setPassword(String password)`: Sets the password of the user.
 * - `toString()`: Returns a string representation of the user, including the username and email.
 *
 * Author: Harrouz Meriem
 */
public class User {
    private String username;
    private String email;
    private String password;

    /**
     * Constructor
     * @param username
     * @param email
     * @param password
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}