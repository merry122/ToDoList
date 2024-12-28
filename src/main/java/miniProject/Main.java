package miniProject;
/**
 * Main - The entry point of the ToDo Application.
 *
 * This class handles the initialization and launching of the JavaFX application. It manages the primary stage,
 * sets the application icon, and provides methods to switch between different views (scenes).
 *
 * Methods:
 * - `start(Stage stage)`: Initializes the primary stage and loads the initial view (Login screen).
 * - `switchView(String fxmlFile)`: Switches the application to a new scene by loading the specified FXML file.
 * - `showErrorDialog(String title, String message)`: Displays error dialog boxes to the user.
 * - `setAppIcon(String iconPath)`: Sets the application icon using the specified image path.
 *
 * Author: Harrouz Meriem
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    private static Stage primaryStage; // Store the primary stage

    @Override
    public void start(Stage stage) {
        primaryStage = stage; // Initialize the primary stage

        // Set the application icon
        setAppIcon("/miniProject/image/to-do-list.png");



        switchView("/miniProject/Login.fxml"); // Load the initial view

        // Set the title of the application
        primaryStage.setTitle("ToDo Application");
    }

    // Method to switch views
    public static void switchView(String fxmlFile) {
        try {
            // Load the new FXML file
            Parent root = FXMLLoader.load(Main.class.getResource(fxmlFile));
            primaryStage.setScene(new Scene(root)); // Set the new scene
            primaryStage.show(); // Show the new scene
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions that occur during loading
            showErrorDialog("Error", "Could not load the view: " + fxmlFile);
        }
    }

    // Method to show an error dialog
    private static void showErrorDialog(String title, String message) {
        // Create a new alert dialog
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to set the application icon
    private void setAppIcon(String iconPath) {
        try {
            Image icon = new Image(getClass().getResourceAsStream(iconPath));
            if (icon != null) {
                primaryStage.getIcons().add(icon);
            } else {
                System.out.println("Icon not found: " + iconPath);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions
        }
    }



    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
