package miniProject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label; // Use Label here instead of Text

public class DisplayTaskController {

    @FXML
    private ListView<String> DisplayTaskListView;

    @FXML
    private Button DisplayTaskAddButton;

    @FXML
    private CheckBox DisplayListiscompletedCheckBox;

    @FXML
    private Label DisplayTaskLabel1; // Corrected to Label instead of Text

    @FXML
    private ImageView TaskImageView;

    @FXML
    private void initialize() {
        // Initialize your tasks here and populate the ListView
        DisplayTaskListView.getItems().addAll("Task 1", "Task 2", "Task 3");
        DisplayTaskLabel1.setText("Task List:"); // Setting the text for Label

        // Optionally load images
        TaskImageView.setImage(new Image("path/to/your/image.png"));
    }

    @FXML
    private void handleDeleteButtonAction() {
        // Logic for deleting selected task
        System.out.println("Delete button clicked.");
    }

    @FXML
    private void handleEditButtonAction() {
        // Logic for editing the selected task
        System.out.println("Edit button clicked.");
    }
}
