package miniProject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage; // Reference to the primary stage of the application

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage; // Set the static reference to the stage
        primaryStage.setTitle("Task Management App");

        // Load the initial screen: Add Item Page
        switchView("/miniProject/AddItem.fxml");
        primaryStage.show();
    }

    /**
     * Method to switch views by loading an FXML file.
     * @param fxmlPath Path to the FXML file to load.
     * @throws Exception Throws an exception if the FXML file is invalid or not found.
     */
    public static void switchView(String fxmlPath) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args); // Launch JavaFX application
    }
}
