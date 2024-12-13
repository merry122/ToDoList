package miniProject;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditTaskController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField dueDateField;

    @FXML
    private TextField completedField;

    private TaskImpl taskToEdit; // The task that the user is editing
    private boolean isUpdated = false; // Flag to track if the task was updated

    // Method to set the task to be edited
    public void setTask(TaskImpl task) {
        this.taskToEdit = task;
        nameField.setText(task.getName());
        descriptionField.setText(task.getDescription());
        dueDateField.setText(String.valueOf(task.getDueDate()));
        completedField.setText(String.valueOf(task.isCompleted()));
    }

    // Handle the save button action
    @FXML
    private void handleSaveAction() {
        try {
            // Update task details
            taskToEdit.setName(nameField.getText());
            taskToEdit.setDescription(descriptionField.getText());
            taskToEdit.setDueDate(dueDateField.getText());
            taskToEdit.setCompleted(Boolean.parseBoolean(completedField.getText()));

            // Mark the task as updated
            isUpdated = true;

            // Close the edit window
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Show error alert if update fails
        }
    }

    // Handle the cancel button action
    @FXML
    private void handleCancelAction() {
        // Close the edit window without saving changes
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    // Getter for checking if the task was updated
    public boolean isUpdated() {
        return isUpdated;
    }
}
