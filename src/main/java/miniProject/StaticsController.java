package miniProject;
/**
 * StaticsController - Controller responsible for displaying statistical data related to tasks.
 *
 * This controller handles the creation and management of charts (BarChart and PieChart) that visualize task-related statistics,
 * such as tasks completed per day and tasks by category. It retrieves data from the TaskListImpl and populates the charts accordingly.
 *
 * Methods:
 * - `initialize()`: Initializes the controller and loads the chart data.
 * - `loadChartData()`: Loads both the tasks per day chart and tasks by category chart.
 * - `loadTasksPerDayChart()`: Generates and populates a bar chart showing the number of completed tasks per day.
 * - `loadTasksByCategoryChart()`: Generates and populates a pie chart showing the distribution of tasks by category.
 * - `handleHomeButton()`, `handleDisplayButton()`, `handleStaticsButton()`, `handleNotificationsButton()`, `handleBackButton()`:
 *   Navigate between different views in the application.
 * - `navigateTo(String fxmlFile)`: Handles navigation between different FXML views.
 * - `showAlert(String title, String message)`: Displays informational alert dialogs.
 *
 * Author: Krama Raouane (for database handling)
 * Author: Harrouz Meriem (for Java implementation)
 * */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaticsController {

    @FXML
    private Button buttonHome; // Button to go home
    @FXML
    private Button buttonDisplay; // Button to display tasks
    @FXML
    private Button buttonStatics; // Button for statistics
    @FXML
    private Button buttonNotifications; // Button for notifications
    @FXML
    private Button backButton; // Back button to return to the previous page

    @FXML
    private BarChart<String, Number> tasksPerDayChart; // Bar chart for tasks completed per day
    @FXML
    private PieChart tasksByCategoryChart; // Pie chart for distribution of tasks by category

    private TaskListImpl taskList; // Instance of TaskListImpl

    public StaticsController() {
        taskList = new TaskListImpl(); // Initialize TaskListImpl
    }

    @FXML
    private void initialize() {
        loadChartData();
    }

    private void loadChartData() {
        loadTasksPerDayChart();
        loadTasksByCategoryChart();
    }

    private void loadTasksPerDayChart() {
        // Example data: Replace this with actual data from your TaskListImpl
        Map<LocalDate, Integer> tasksCompletedPerDay = new HashMap<>();
        List<TaskImpl> tasks = taskList.getAll(); // Get all tasks

        for (TaskImpl task : tasks) {
            if (task.isCompleted()) { // Assuming there's a method to check if a task is completed
                LocalDate completionDate = task.getCompletionDate(); // Assuming you have a method to get the completion date
                tasksCompletedPerDay.put(completionDate, tasksCompletedPerDay.getOrDefault(completionDate, 0) + 1);
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<LocalDate, Integer> entry : tasksCompletedPerDay.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
        }
        tasksPerDayChart.getData().add(series);
    }

    private void loadTasksByCategoryChart() {
        // Example data: Replace this with actual data from your TaskListImpl
        Map<String, Integer> tasksByCategory = new HashMap<>();
        List<TaskImpl> tasks = taskList.getAll(); // Get all tasks

        for (TaskImpl task : tasks) {
            String category = String.valueOf(task.getCategory()); // Assuming you have a method to get the task's category
            tasksByCategory.put(category, tasksByCategory.getOrDefault(category, 0) + 1);
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : tasksByCategory.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        tasksByCategoryChart.setData(pieChartData);
    }

    @FXML
    private void handleHomeButton() {
        navigateTo("/miniProject/Home.fxml"); // Navigate to the home view
    }

    @FXML
    private void handleDisplayButton() {
        navigateTo("/miniProject/Display.fxml"); // Navigate to the display view
    }

    @FXML
    private void handleStaticsButton() {
        // This button is already on the statics page, so you might not need to do anything here
    }

    @FXML
    private void handleNotificationsButton() {
        navigateTo("/miniProject/NotificationS.fxml"); // Navigate to the notifications view
    }

    @FXML
    private void handleBackButton() {
        navigateTo("/miniProject/Home.fxml"); // Navigate back to the home view
    }

    private void navigateTo(String fxmlFile) {
        try {
            System.out.println("Attempting to load FXML file: " + fxmlFile); // Debug log
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));

            if (getClass().getResource(fxmlFile) == null) {
                throw new IOException("FXML file not found: " + fxmlFile);
            }

            Parent newView = loader.load();
            Stage currentStage = (Stage) buttonHome.getScene().getWindow(); // Ensure buttonHome is properly bound
            currentStage.setScene(new Scene(newView));
            currentStage.show();
        } catch (NullPointerException e) {
            System.err.println("NullPointerException: Check fx:id bindings in the FXML file.");
            e.printStackTrace();
            showAlert("Navigation Error", "A required component was not properly initialized. Check your FXML file and code.");
        } catch (IOException e) {
            System.err.println("IOException: Unable to load the FXML file.");
            e.printStackTrace();
            showAlert("Navigation Error", "Unable to load the requested view. Ensure the file exists and the path is correct: " + fxmlFile);
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}