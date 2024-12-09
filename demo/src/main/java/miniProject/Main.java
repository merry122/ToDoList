package miniProject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage primaryStage; // Central stage for switching views

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        // Load the initial view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/miniProject/AddItem.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("ToDo Application");
        primaryStage.show();
    }
package miniProject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage primaryStage; // Central stage for switching views

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        // Load the initial view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/miniProject/AddItem.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("ToDo Application");
        primaryStage.show();
    }

    public static void switchView(String fxmlFile) throws Exception {
        // Method to switch views
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

    public static void switchView(String fxmlFile) throws Exception {
        // Method to switch views
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
