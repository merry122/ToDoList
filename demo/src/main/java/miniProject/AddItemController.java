package miniProject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AddItemController {
    @FXML
    private Label AddItemLabel;

    @FXML
    private Button AddItemButton;

    @FXML
    private void handleAddTaskAction() {
        // Logic for adding a task (e.g., navigating to task creation screen)
        AddItemLabel.setText("Task added successfully!");  // Temporary feedback
        System.out.println("Task button clicked, add your task here.");
    }
}
