package miniProject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;

public class DisplayTaskController {

    @FXML
    private TableView<TaskImpl> tasksTable;

    @FXML
    private TableColumn<TaskImpl, Integer> idColumn;

    @FXML
    private TableColumn<TaskImpl, String> nameColumn;

    @FXML
    private TableColumn<TaskImpl, String> descriptionColumn;

    @FXML
    private TableColumn<TaskImpl, String> dueDateColumn;

    @FXML
    private TableColumn<TaskImpl, Boolean> completedColumn;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton, sortByDueDateButton, backButton;

    private TaskListImpl taskListImpl = new TaskListImpl();

    @FXML
    public void initialize() {
        System.out.println("Initializing the DisplayTaskController...");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        completedColumn.setCellValueFactory(new PropertyValueFactory<>("completed"));

        System.out.println("Columns initialized successfully.");

        loadTasks(); // Problem is likely here
    }

    private void loadTasks() {
        try {
            System.out.println("Attempting to load tasks from TaskListImpl...");
            List<TaskImpl> taskList = taskListImpl.getAll();

            if (taskList == null || taskList.isEmpty()) {
                System.out.println("No tasks returned from TaskListImpl.");
                tasksTable.setItems(FXCollections.observableArrayList()); // Clear the table
                showAlert(Alert.AlertType.INFORMATION, "No tasks found in the database.");
                return;
            }

            ObservableList<TaskImpl> taskObservableList = FXCollections.observableArrayList(taskList);
            tasksTable.setItems(taskObservableList);
            System.out.println("Tasks loaded successfully: " + taskObservableList.size());
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading tasks: " + e.getMessage());
        }
    }

    @FXML
    private void handleSearchAction() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            loadTasks();
            return;
        }
        ObservableList<TaskImpl> searchedTasks = FXCollections.observableArrayList(taskListImpl.searchByName(searchText));
        tasksTable.setItems(searchedTasks);
        if (searchedTasks.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "No tasks found for search term: " + searchText);
        }
    }

    @FXML
    private void handleSortByDueDateAction() {
        ObservableList<TaskImpl> sortedTasks = FXCollections.observableArrayList(taskListImpl.sortByDueDate());
        tasksTable.setItems(sortedTasks);
    }

    @FXML
    private void handleBackAction() {
        try {
            // Load the TaskPage.fxml file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Task.fxml"));
            Parent taskPageRoot = fxmlLoader.load();

            // Get the current stage (from the backButton)
            Stage currentStage = (Stage) backButton.getScene().getWindow();

            // Set the scene to the Task Page
            Scene scene = new Scene(taskPageRoot);
            currentStage.setScene(scene);

            // Optional: Set the title for the Task Page window
            currentStage.setTitle("Task Page");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error: Failed to load Task Page!");
        }
    }

    // Helper method for showing error alerts
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private Button editButton; // The new edit button

    @FXML
    private void handleEditAction() {
        // Get the selected task from the TableView
        TaskImpl selectedTask = tasksTable.getSelectionModel().getSelectedItem();

        if (selectedTask == null) {
            showAlert(Alert.AlertType.WARNING, "No task selected. Please select a task to edit.");
            return;
        }

        try {
            // Load the EditTask.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditTask.fxml"));
            Parent editTaskRoot = loader.load();

            // Get the EditTaskController and pass the selected task to it
            EditTaskController controller = loader.getController();
            controller.setTask(selectedTask); // Provide the selected task for editing

            // Show the edit form in a new window
            Stage stage = new Stage();
            stage.setScene(new Scene(editTaskRoot));
            stage.setTitle("Edit Task");
            stage.showAndWait(); // Wait for the user to close the form

            // Check if the user made changes and saved the task
            if (controller.isUpdated()) {
                // Call the editTask method to save changes to the database
                taskListImpl.editTask(selectedTask);

                // Refresh the TableView to show the updated task
                loadTasks();

                // Notify the user
                showAlert(Alert.AlertType.INFORMATION, "Task updated successfully!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error occurred while opening the edit form.");
        }
    }

    @FXML
    private void handleDeleteAction() {
        // Get the selected task from the TableView
        TaskImpl selectedTask = tasksTable.getSelectionModel().getSelectedItem();

        if (selectedTask == null) {
            // Alert the user if no task is selected
            showAlert(Alert.AlertType.WARNING, "No task selected. Please select a task to delete.");
            return;
        }

        // Show confirmation dialog before deletion
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Are you sure you want to delete the selected task?");
        confirmationAlert.setContentText("Task: " + selectedTask.getName());

        // Check if user confirmed deletion
        if (confirmationAlert.showAndWait().get() == ButtonType.OK) {
            // Call the deleteTask method
            taskListImpl.deleteTask(selectedTask);

            // Refresh the TableView to reflect changes
            loadTasks();

            // Notify the user
            showAlert(Alert.AlertType.INFORMATION, "Task deleted successfully!");
        }
    }

}
