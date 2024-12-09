package miniProject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class TaskController {

    @FXML
    private TextField TaskNewTask;  // TextField for entering task name

    @FXML
    private DatePicker TaskDateTask;  // DatePicker for task due date

    @FXML
    private Button TaskAddButton;  // Button to add the task

    @FXML
    private TextArea TaskDescription;  // TextArea for task description

    @FXML
    private Button TaskDisplayButton;  // Button for displaying tasks

    @FXML
    private Button TaskDeletButton;  // Button for deleting tasks

    @FXML
    private Button BackToAddItemButton;  // Back button for navigating back to task creation

    @FXML
    private Label TaskDisplayLabel;  // Label for displaying information

    @FXML
    private void handleTaskAddButton() {
        String taskName = TaskNewTask.getText();
        String taskDate = TaskDateTask.getValue() != null ? TaskDateTask.getValue().toString() : "No date";
        String taskDescription = TaskDescription.getText();

        // Add the task details (e.g., to a list or database)
        System.out.println("Task Added: " + taskName + ", Due: " + taskDate + ", Description: " + taskDescription);

        // Clear the fields after adding the task
        TaskNewTask.clear();
        TaskDateTask.setValue(null);
        TaskDescription.clear();
    }

    @FXML
    private void handleDisplayButtonAction() {
        // Example of displaying task information
        String taskName = TaskNewTask.getText();
        String taskDate = TaskDateTask.getValue() != null ? TaskDateTask.getValue().toString() : "No date";
        String taskDescription = TaskDescription.getText();

        // Display the task information in a Label
        String taskDetails = "Task: " + taskName + "\nDue: " + taskDate + "\nDescription: " + taskDescription;
        TaskDisplayLabel.setText(taskDetails);  // Update the label with task details

        // Optionally, print to console for debugging
        System.out.println("Display Task: " + taskDetails);
    }

    @FXML
    private void handleBackToAddItem() {
        // Logic to go back to the task creation screen
        // For example, clearing all fields to reset the form
        TaskNewTask.clear();
        TaskDateTask.setValue(null);
        TaskDescription.clear();

        // Optionally, if you want to hide or show certain UI elements, or navigate between views,
        // you can do that here. For example:
        System.out.println("Back to task creation screen.");
    }
}
