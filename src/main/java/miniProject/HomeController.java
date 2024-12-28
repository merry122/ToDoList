package miniProject;
/**
 * HomeController - Controller for managing tasks in the home screen of the application.
 *
 * This controller handles task creation, input validation, and navigation between different views.
 * It interacts with the TaskListImpl to add, retrieve, and manage tasks stored in a database.
 *
 * Methods:
 * - `handleAddTask()`: Adds a new task with the provided input and validates it.
 * - `generateTaskId()`: Generates unique IDs for tasks.
 * - `clearFields()`: Clears input fields after adding a task.
 * - `loadPage(String page)`: Loads a new FXML page to switch views.
 * - `showAlert(String title, String message)`: Displays informational alerts.
 * - Navigation button handlers (e.g., `handleAddButton()`, `handleDisplayButton()`, etc.): Handle navigation between different sections of the app.
 *
 * Author: Krama Raouane (for database handling)
 * Author: Harrouz Meriem (for Java implementation)
 */
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.time.LocalDate;

public class HomeController {

    @FXML
    private TextField teaskentring; // TextField for task input
    @FXML
    private DatePicker dateoftask; // DatePicker for due date
    @FXML
    private TextArea description; // TextArea for task description
    @FXML
    private ComboBox<TaskImpl.Priority> priority; // ComboBox for task priority
    @FXML
    private ComboBox<TaskImpl.Category> categorie; // ComboBox for task category
    @FXML
    private Text texttaskmanage; // Text for task management
    @FXML
    private Button addbutton; // Button to add task

    private TaskListImpl taskList; // Instance of TaskListImpl
    private static int taskCounter = 1; // Static counter for unique task IDs

    public HomeController() {
        taskList = new TaskListImpl(); // Initialize TaskListImpl
    }

    @FXML
    private void initialize() {
        // Populate the priority ComboBox
        priority.getItems().addAll(TaskImpl.Priority.values());

        // Populate the category ComboBox
        categorie.getItems().addAll(TaskImpl.Category.values());
    }

    // Method to handle adding a task
    @FXML
    private void handleAddTask() {
        String taskName = teaskentring.getText();
        String taskDescription = description.getText();
        LocalDate dueDate = dateoftask.getValue();
        TaskImpl.Priority taskPriority = priority.getValue();
        TaskImpl.Category taskCategory = categorie.getValue();

        // Validate input
        if (taskName.isEmpty() || taskDescription.isEmpty() || dueDate == null || taskPriority == null || taskCategory == null) {
            showAlert("Input Error", "Please fill in all fields.");
            return;
        }

        // Generate a unique ID for the task
        int taskId = generateTaskId();
        System.out.println("Generated Task ID: " + taskId); // Debugging statement

        // Create a new TaskImpl object
        TaskImpl newTask = new TaskImpl(taskId, taskName, taskDescription, dueDate, TaskImpl.Status.PENDING, taskPriority, taskCategory);

        // Add the task to the database
        if (taskList.addTask(newTask)) {
            showAlert("Success", "Task added successfully!");
            clearFields(); // Clear the input fields after adding the task
        } else {
            showAlert("Error", "Failed to add task to the database.");
        }
    }

    private int generateTaskId() {
        return taskCounter++; // Increment and return the counter
    }

    private void clearFields() {
        description.clear();
        priority.getSelectionModel().clearSelection();
        categorie.getSelectionModel().clearSelection();
        dateoftask.setValue(null);
    }

    // Method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to handle the Add button in the sidebar
    @FXML
    private void handleAddButton() {
        // Logic to handle adding a new task
        System.out.println("Add button clicked");
    }

    // Method to handle the Display button in the sidebar
    @FXML
    private void handleDisplayButton() {
        loadPage("Display.fxml");
    }

    // Method to handle the Statics button in the sidebar
    @FXML
    private void handleStaticsButton() {
        loadPage("Statics.fxml");
    }

    // Method to handle the Notifications button in the sidebar
    @FXML
    private void handleNotificationsButton() {
        loadPage("Notification.fxml");
    }

    // Method to handle the Import button in the sidebar
    @FXML
    private void handleImportButton() {
        loadPage("Import.fxml");
    }

    // Method to handle the Exit button in the sidebar
    @FXML
    private void handleExitButton() {
        System.out.println("Exit button clicked");
        javafx.application.Platform.exit(); // Close the application
    }

    // Method to load a new page
    private void loadPage(String page) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(page));
            Stage stage = (Stage) addbutton.getScene().getWindow(); // Get the current stage
            stage.setScene(new Scene(root)); // Set the new scene
            stage.show(); // Show the new scene
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}