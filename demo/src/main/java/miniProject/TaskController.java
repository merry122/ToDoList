package miniProject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.sql.*;

public class TaskController {

    @FXML
    private TextField TaskNewTask;  // TextField for task name

    @FXML
    private DatePicker TaskDateTask;  // DatePicker for task date

    @FXML
    private TextArea TaskNewDescription;  // TextArea for task description

    @FXML
    private Button BackToAddItemButton;  // Button to navigate back

    @FXML
    private Button TaskDisplayButton;  // Button to display tasks

    @FXML
    private Button TaskAddButton;  // Button to add task to database

    private TaskListImpl taskList = new TaskListImpl();  // Your task list that interacts with the database

    // Static counter to track the next task ID
    private static int taskIdCounter = 1;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/to_do_list";  // Replace with your database URL
    private static final String DB_NAME = "root";  // Replace with your database username
    private static final String PASSWORD = "";  // Replace with your database password


    @FXML
    private StackPane sidebarContainer; // Sidebar container
    @FXML
    private Button burgerMenuButton;    // Burger menu button

    // Method to toggle sidebar visibility
    @FXML
    private void toggleSidebar() {
        // Check current visibility and toggle
        boolean isVisible = sidebarContainer.isVisible();
        sidebarContainer.setVisible(!isVisible);
        sidebarContainer.setManaged(!isVisible); // Adjust layout when toggling
    }


    // Method to go back to AddItem view
    @FXML
    private void handleBackToAddItem() {
        try {
            Main.switchView("/miniProject/AddItem.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to go to DisplayTask view
    @FXML
    private void handleDisplayButtonAction() {
        try {
            Main.switchView("/miniProject/DisplayTask.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to handle task adding
    @FXML
    public void handleTaskAddButton(ActionEvent actionEvent) {
        // Collecting task details from the fields
        String taskName = TaskNewTask.getText();
        String taskDate = (TaskDateTask.getValue() != null) ? TaskDateTask.getValue().toString() : "No date selected";
        String taskDescription = TaskNewDescription.getText();

        // Validate input
        if (taskName.isEmpty() || taskDescription.isEmpty() || taskDate.equals("No date selected")) {
            System.out.println("Please fill in all fields.");
            return;
        }

        // Generate a task ID (for now, we'll just use current time)
        int taskId = generateTaskId();

        // Create a new task instance
        TaskImpl task = new TaskImpl(taskId, taskName, taskDescription, taskDate);

        // Add the task to the database using TaskListImpl
        taskList.addTask(task);

        // Clear the fields after adding the task
        TaskNewTask.clear();
        TaskDateTask.setValue(null);
        TaskNewDescription.clear();

        // Provide feedback to the user
        System.out.println("Task added to database:");
        System.out.println("Name: " + taskName);
        System.out.println("Date: " + taskDate);
        System.out.println("Description: " + taskDescription);
    }

    // Helper method to generate task ID (you can replace this with your database's auto-increment logic)
    private int generateTaskId() {
        int lastId = getLastTaskIdFromDatabase(); // Method to get the last task ID from the database
        return lastId + 1;  // Simple approach using system time (milliseconds)
    }

    private int getLastTaskIdFromDatabase() {
        int lastId = 0;  // Default value in case no tasks exist

        // Database connection
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_NAME,PASSWORD)) {
            // SQL query to get the maximum task ID
            String query = "SELECT MAX(id) FROM tasks";  // Replace 'tasks' with your table name

            // Create a statement and execute the query
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                if (rs.next()) {
                    lastId = rs.getInt(1);  // Get the maximum ID from the result set
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle exceptions
        }

        return lastId;  // Return the last task ID, or 0 if no tasks exist
    }


}
