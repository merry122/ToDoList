package miniProject;
/**
 * EditController - Controller for editing tasks in the application.
 *
 * This class handles the logic for displaying and editing tasks via the user interface.
 * It manages form fields for task name, description, due date, priority, and category,
 * and interacts with the TaskListImpl to update or delete tasks in the database.
 *
 * Methods:
 * - `initialize(TaskImpl task)`: Initializes the controller with the task details.
 * - `populateComboBoxes()`: Populates the priority and category ComboBoxes with their respective values.
 * - `handleSave()`: Saves the edited task to the database.
 * - `handleDelete()`: Deletes the current task from the database.
 * - `setTask(TaskImpl selectedTask)`: Sets the current task to be edited.
 * - `closeWindow()`: Closes the current edit window.
 * - `showAlert(String title, String message)`: Displays informational alerts to the user.
 *
 * Author: Krama Raouane (for database handling)
 * Author: Harrouz Meriem (for Java implementation)
 */
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EditController {

    @FXML
    private TextField textFieldName; // TextField for task name
    @FXML
    private TextField textFieldDescription; // TextField for task description
    @FXML
    private TextField textFieldDueDate; // TextField for due date
    @FXML
    private ComboBox<TaskImpl.Priority> priority; // ComboBox for task priority
    @FXML
    private ComboBox<TaskImpl.Category> category; // ComboBox for task category
    @FXML
    private Button buttonSave; // Button to save changes
    @FXML
    private Button buttonDelete; // Button to delete the task
    @FXML
    private Text taskHeader; // Text for the header

    private TaskImpl currentTask; // The task being edited
    private TaskListImpl taskList; // Instance of TaskListImpl

    public EditController() {
        taskList = new TaskListImpl(); // Initialize TaskListImpl
    }

    // Method to initialize the controller with the task data
    public void initialize(TaskImpl task) {
        this.currentTask = task;
        textFieldName.setText(task.getName());
        textFieldDescription.setText(task.getDescription());
        textFieldDueDate.setText(task.getDueDate().toString());
        priority.setValue(task.getPriority());
        category.setValue(task.getCategory());

        // Populate ComboBoxes if not already done
        populateComboBoxes();
    }

    private void populateComboBoxes() {
        priority.getItems().setAll(TaskImpl.Priority.values()); // Populate priority ComboBox
        category.getItems().setAll(TaskImpl.Category.values()); // Populate category ComboBox
    }

    // Method to handle saving the edited task
    @FXML
    private void handleSave() {
        try {
            // Update the current task with the new values
            currentTask.setName(textFieldName.getText());
            currentTask.setDescription(textFieldDescription.getText());
            currentTask.setDueDate(LocalDate.parse(textFieldDueDate.getText())); // Parse the due date
            currentTask.setPriority(priority.getValue());
            currentTask.setCategory(category.getValue());

            // Save the updated task to the database
            if (taskList.editTask(currentTask)) {
                System.out.println("Task updated in the database!");
                closeWindow(); // Close the edit window after saving
            } else {
                System.err.println("Failed to update the task.");
                showAlert("Error", "Failed to update the task in the database.");
            }
        } catch (DateTimeParseException e) {
            showAlert("Invalid Date", "Please enter a valid date in the format YYYY-MM-DD.");
        }
    }

    // Method to handle deleting the task
    @FXML
    private void handleDelete() {
        taskList.deleteTask(currentTask.getId()); // Assuming deleteTask takes an ID
        System.out.println("Task deleted from the database!");
        closeWindow(); // Close the edit window after deletion
    }

    // Method to set the current task
    public void setTask(TaskImpl selectedTask) {
        this.currentTask = selectedTask;
        initialize(selectedTask); // Initialize the fields with the selected task's data
    }

    // Helper method to close the current window
    private void closeWindow() {
        Stage stage = (Stage) buttonSave.getScene().getWindow(); // Get the current stage
        stage.close(); // Close the window
    }

    // Helper method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}