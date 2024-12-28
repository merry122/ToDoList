package miniProject;
/**
 * LoginController - Controller responsible for managing the login functionality of the application.
 *
 * This class handles user authentication by validating credentials against a database.
 * It manages the login process, redirecting to the main To-Do List interface upon successful login.
 * It also provides methods to navigate to the Sign-Up page and display appropriate alerts.
 *
 * Methods:
 * - `handleLoginClick(ActionEvent event)`: Processes the login form submission and validates the user credentials.
 * - `handleSignupRedirect(ActionEvent event)`: Redirects the user to the Sign-Up page.
 * - `validateUser(String username, String password)`: Validates the user credentials from the database.
 * - `navigateToMain()`: Redirects to the main To-Do List interface.
 * - `showAlert(Alert.AlertType alertType, String title, String message)`: Displays alert messages to the user.
 *
 * Author: Krama Raouane (for database handling)
 * Author: Harrouz Meriem (for Java implementation)
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private PasswordField passwordtextfeild;
    @FXML
    private TextField textfeild;
    @FXML
    private Button loginbutton;
    @FXML
    private Hyperlink hypertextsignup;
    @FXML
    private Text textwelcome;

    // Called when the Login button is clicked
    @FXML
    private void handleLoginClick(ActionEvent event) {
        String username = textfeild.getText();
        String password = passwordtextfeild.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Please fill in both fields.");
            return;
        }

        // Validate the user credentials from the database
        if (validateUser (username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Success", "Welcome, " + username + "!");
            // Navigate to the main application (To-Do List)
            navigateToMain();
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    // Navigate to the Sign-Up page
    @FXML
    private void handleSignupRedirect(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/miniProject/Signup.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) loginbutton.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Sign Up");

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the Sign-Up page.");
        }
    }

    // Validate user credentials with the database
    private boolean validateUser (String username, String password) {
        Connection con = DBconnection.getConnection();
        String query = "SELECT * FROM users WHERE username = ? AND password = ?"; // Consider hashing passwords

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password); // Hash the password in a real application

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while connecting to the database.");
        } finally {
            try {
                if (con != null) {
                    con.close(); // Ensure the connection is closed
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // Navigate to the main To-Do List interface
    private void navigateToMain() {
        try {
            // Load the Main To-Do List page (ensure Display.fxml exists)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/miniProject/Display.fxml")); // Ensure the path is correct
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) loginbutton.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("To-Do List");

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the To-Do List interface.");
        }
    }

    // Utility method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null );
        alert.setContentText(message);
        alert.showAndWait();
    }
}