package miniProject;
/**
 * SignupController - Controller responsible for handling the user sign-up process.
 *
 * This controller manages user input, form validation, and database interactions to create new user accounts.
 *
 * Methods:
 * - `handleSignup(ActionEvent event)`: Processes the sign-up form submission, validates input, and attempts to save the user to the database.
 * - `saveUser(String email, String username, String password)`: Saves the user information to the database and returns whether the operation was successful.
 * - `navigateToLogin()`: Navigates the user back to the login page after successful sign-up.
 * - `showAlert(Alert.AlertType alertType, String title, String message)`: Utility method to display alert messages to the user.
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
import java.sql.SQLException;

public class SignupController {

    @FXML
    private TextField maitextfeild;
    @FXML
    private TextField usernametextfeild;
    @FXML
    private PasswordField passwordtextfeild;
    @FXML
    private Button buttonsignup;
    @FXML
    private Text TextWelcoming;

    @FXML
    private void handleSignup(ActionEvent event) {
        String email = maitextfeild.getText();
        String username = usernametextfeild.getText();
        String password = passwordtextfeild.getText();

        // Validate form fields
        if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Sign-Up Error", "All fields are required.");
            return;
        }

        // Save the user into the database
        boolean isSaved = saveUser(email, username, password);

        if (isSaved) {
            showAlert(Alert.AlertType.INFORMATION, "Sign-Up Success", "Account created successfully. Please log in.");
            navigateToLogin();
        } else {
            showAlert(Alert.AlertType.ERROR, "Sign-Up Failed", "Could not create an account. Try again.");
        }
    }

    private boolean saveUser(String email, String username, String password) {
        Connection con = DBconnection.getConnection(); // Ensure this method returns a valid DB connection

        String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            // Set parameters for the prepared statement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            // Execute the update
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User saved successfully: " + username);
                return true;
            } else {
                System.out.println("Failed to save user: " + username);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error saving user to the database: " + e.getMessage());
            return false;
        }
    }

    private void navigateToLogin() {
        try {
            // Load the Login page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) buttonsignup.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the Login page.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
