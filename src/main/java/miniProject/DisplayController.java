package miniProject;
/**
 * DisplayController - Controller for managing and displaying tasks in the application.
 *
 * This class handles the interaction between the user interface and the underlying
 * data model (TaskListImpl). It manages the display, search, sorting, and filtering of tasks
 * using JavaFX components.
 *
 * Methods:
 * - `initialize()`: Initializes the controller, sets up the TableView columns, and loads tasks.
 * - `loadTasks()`: Fetches all tasks from the database and updates the TableView.
 * - `handleSortButton()`: Sorts tasks by due date and updates the TableView with the sorted results.
 * - `handleSearchButton()`: Searches for tasks by name and updates the TableView with the search results.
 * - `filterTasksByCategory(String selectedCategory)`: Filters tasks based on the selected category and updates the TableView.
 * - `handleEditButton()`: Opens the Edit page to edit the selected task.
 * - `handleDeleteButton()`: Deletes the selected task and reloads the tasks from the database.
 * - `navigateTo(String fxmlFile)`: Helper method to load new pages in the application.
 * - `showAlert(String title, String message)`: Displays informational alerts to the user.
 * - `handleHomeButton(ActionEvent actionEvent)`, `handleStaticsButton(ActionEvent actionEvent)`, `handleNotificationsButton(ActionEvent actionEvent)`: Navigate to different sections of the application.
 *
 * Author: Krama Raouane (for database handling)
 * Author: Harrouz Meriem (for Java implementation)
 */
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DisplayController {

    @FXML
    private Button buttonHome;
    @FXML
    private Button buttonDisplay;
    @FXML
    private Button buttonStatics;
    @FXML
    private Button buttonNotifications;
    @FXML
    private Button buttonSort;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonEdit;
    @FXML
    private Button buttonDelete;
    @FXML
    private TextField searchTextField; // Corrected spelling
    @FXML
    private ComboBox<String> categoryFilter; // Updated to match your FXML id
    @FXML
    private TableView<TaskImpl> tabletasks; // Use TaskImpl as the type
    @FXML
    private TableColumn<TaskImpl, String> task; // Task column
    @FXML
    private TableColumn<TaskImpl, String> dueDate; // Due date column
    @FXML
    private TableColumn<TaskImpl, String> categoryColumn; // Category column
    @FXML
    private TableColumn<TaskImpl, String> priority; // Priority column
    @FXML
    private TableColumn<TaskImpl, String> description; // Description column

    private TaskListImpl taskList = new TaskListImpl();

    @FXML
    private void initialize() {
        loadTasks();
        setupTableColumns();
        populateCategoryComboBox(); // Call the method to populate the ComboBox

        // Add listener to the category filter ComboBox
        categoryFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            filterTasksByCategory(newValue);
        });
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTasksBySearch(newValue);
        });
    }
    private void filterTasksBySearch(String searchText) {
        ObservableList<TaskImpl> filteredTasks = FXCollections.observableArrayList();

        // If the search text is empty, load all tasks
        if (searchText == null || searchText.isEmpty()) {
            loadTasks();
            return;
        }

        // Filter tasks based on the search text
        for (TaskImpl task : taskList.getAll()) {
            if (task.getName().toLowerCase().contains(searchText.toLowerCase()) ||
                    task.getDescription().toLowerCase().contains(searchText.toLowerCase())) {
                filteredTasks.add(task);
            }
        }

        tabletasks.setItems(filteredTasks);
        if (filteredTasks.isEmpty()) {
            showAlert("No Results", "No tasks found matching: " + searchText);
        }
    }

    private void populateCategoryComboBox() {
        categoryFilter.getItems().clear(); // Clear existing items
        for (TaskImpl.Category cat : TaskImpl.Category.values()) {
            categoryFilter.getItems().add(cat.toString()); // Add the category names to the ComboBox
            System.out.println("Added category: " + cat); // Debugging statement
        }
    }

    private void loadTasks() {
        List<TaskImpl> tasks = taskList.getAll();
        ObservableList<TaskImpl> observableTasks = FXCollections.observableArrayList(tasks);
        tabletasks.setItems(observableTasks);
    }

    private void setupTableColumns() {
        task.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        dueDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDueDate().toString())); // Convert LocalDate to String
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().toString())); // Assuming Category has a toString method
        priority.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPriority().toString())); // Assuming Priority has a toString method
        description.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
    }

    // Action for Edit button
    @FXML
    private void handleEditButton() {
        TaskImpl selectedTask = tabletasks.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            try {
                // Load the FXML for the edit window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/miniProject/Edit.fxml")); // Ensure the path is correct
                Parent editView = loader.load();

                // Get the controller and set the current task
                EditController editController = loader.getController();
                editController.setTask(selectedTask); // Ensure this is called after loading the FXML

                // Create a new stage for the edit window
                Stage editStage = new Stage();
                editStage.setTitle("Edit Task");
                editStage.setScene(new Scene(editView));
                editStage.showAndWait(); // Show the edit window and wait for it to close

                // Refresh the task list after editing
                loadTasks();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Could not open the edit window.");
            }
        } else {
            showAlert("No Task Selected", "Please select a task to edit.");
        }
    }

    // Action for Delete button
    @FXML
    private void handleDeleteButton() {
        TaskImpl selectedTask = tabletasks.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            // Confirm deletion
            Alert confirmation = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this task?");
            confirmation.setTitle("Delete Task");
            confirmation.setHeaderText(null);
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    taskList.deleteTask(selectedTask.getId()); // Assuming deleteTask takes an ID
                    loadTasks(); // Refresh the table
                    showAlert("Task Deleted", "Task has been deleted successfully.");
                }
            });
        } else {
            showAlert("No Task Selected", "Please select a task to delete.");
        }
    }

    // Action for Sort button
    @FXML
    private void handleSortButton() {
        List<TaskImpl> sortedTasks = taskList.sortByDueDate();
        ObservableList<TaskImpl> observableTasks = FXCollections.observableArrayList(sortedTasks);
        tabletasks.setItems(observableTasks);
        showAlert("Tasks Sorted", "Tasks have been sorted by due date.");
    }

    // Action for Search button
    @FXML
    private void handleSearchButton() {
        String searchText = searchTextField.getText().toLowerCase(); // Corrected spelling
        ObservableList<TaskImpl> filteredTasks = FXCollections.observableArrayList();

        for (TaskImpl task : taskList.getAll()) { // Use taskList.getAll() to get all tasks
            if (task.getName().toLowerCase().contains(searchText) ||
                    task.getDescription().toLowerCase().contains(searchText)) {
                filteredTasks.add(task);
            }
        }

        tabletasks.setItems(filteredTasks);
        if (filteredTasks.isEmpty()) {
            showAlert("No Results", "No tasks found matching: " + searchText);
        } else {
            showAlert("Search Results", filteredTasks.size() + " tasks found.");
        }
    }

    // Method to filter tasks by selected category
    private void filterTasksByCategory(String selectedCategory) {
        if (selectedCategory == null || selectedCategory.isEmpty()) {
            loadTasks(); // If no category is selected, load all tasks
            return;
        }

        ObservableList<TaskImpl> filteredTasks = FXCollections.observableArrayList();
        for (TaskImpl task : taskList.getAll()) {
            if (task.getCategory().toString().equals(selectedCategory)) {
                filteredTasks.add(task);
            }
        }

        tabletasks.setItems(filteredTasks);
        if (filteredTasks.isEmpty()) {
            showAlert("No Results", "No tasks found for category: " + selectedCategory);
        } else {
            showAlert("Filtered Results", filteredTasks.size() + " tasks found for category: " + selectedCategory);
        }
    }

    // Helper method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Action for Home button
    @FXML
    public void handleHomeButton(ActionEvent actionEvent) {
        navigateTo("/miniProject/Home.fxml"); // Ensure the path is correct
    }

    // Action for Display button
    @FXML
    public void handleDisplayButton(ActionEvent actionEvent) {
        navigateTo("/miniProject/Display.fxml"); // Ensure the path is correct
    }

    // Action for Statistics button
    @FXML
    public void handleStaticsButton(ActionEvent actionEvent) {
        navigateTo("Statics.fxml"); // Ensure the path is correct
    }

    // Method to navigate to a different view
    private void navigateTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newView = loader.load();
            Stage currentStage = (Stage) buttonHome.getScene().getWindow(); // Get the current stage
            currentStage.setScene(new Scene(newView)); // Set the new scene
            currentStage.show(); // Show the new scene
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not load the requested view.");
        }
    }

    // Action for Notifications button
    public void handleNotificationsButton(ActionEvent actionEvent) {
        navigateTo("/miniProject/Notification.fxml");
    }

}