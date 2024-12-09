package miniProject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/miniProject/Task.fxml"));
        AnchorPane root = loader.load();

        // Create a scene and set it on the stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ToDo Application");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
