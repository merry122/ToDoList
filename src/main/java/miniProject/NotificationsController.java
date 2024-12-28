package miniProject;
/**
 * NotificationsController - Controller responsible for managing and displaying task notifications.
 *
 * This class handles the loading and display of task notifications related to due dates.
 * It interacts with the TaskListImpl to fetch tasks and generate notifications for tasks due today or tomorrow.
 *
 * Methods:
 * - `initialize()`: Initializes the controller and loads notifications.
 * - `loadNotifications()`: Loads notifications for tasks due today or tomorrow.
 * - `createNotificationBox(TaskImpl task, String message)`: Creates a notification UI element for a given task and message.
 * - `handleHomeButton()`, `handleDisplayButton()`, `handleStaticsButton()`, `handleBackButton()`: Navigation methods to switch between different views.
 * - `navigateTo(String fxmlFile)`: Navigates to a different view specified by the FXML file path.
 * - `showAlert(String title, String message)`: Displays an information alert to the user.
 * - `handleMarkAsRead(ActionEvent actionEvent)`: Placeholder method for handling marking notifications as read.
 *
 * Author: Harrouz Meriem (for the Java implementation part)
 * Author: Krama Raouane (for the database handling parts)
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class NotificationsController {

    @FXML
    private TextField searchTextField; // Search field for tasks
    @FXML
    private Button buttonHome; // Button to go home
    @FXML
    private Button buttonDisplay; // Button to display tasks
    @FXML
    private Button buttonStatics; // Button for statistics
    @FXML
    private Button buttonNotifications; // Button for notifications
    @FXML
    private ScrollPane scrollPane; // ScrollPane to hold notifications
    @FXML
    private VBox notificationsContainer; // VBox to hold notification HBoxes

    private TaskListImpl taskList; // Instance of TaskListImpl

    public NotificationsController() {
        taskList = new TaskListImpl(); // Initialize TaskListImpl
    }

    @FXML
    private void initialize() {
        loadNotifications();
    }

    private void loadNotifications() {
        notificationsContainer.getChildren().clear(); // Clear existing notifications

        List<TaskImpl> tasks = taskList.getAll(); // Get all tasks
        LocalDate today = LocalDate.now();

        for (TaskImpl task : tasks) {
            LocalDate dueDate = task.getDueDate();
            if (dueDate != null) {
                if (dueDate.isEqual(today)) {
                    // Create a notification for tasks due today
                    HBox notificationBox = createNotificationBox(task, "Task '" + task.getName() + "' is due today!");
                    notificationsContainer.getChildren().add(notificationBox);
                } else if (dueDate.isAfter(today) && dueDate.isBefore(today.plusDays(1))) {
                    // Create a notification for tasks due tomorrow
                    HBox notificationBox = createNotificationBox(task, "Task '" + task.getName() + "' is due tomorrow!");
                    notificationsContainer.getChildren().add(notificationBox);
                }
            }
        }
    }

    private HBox createNotificationBox(TaskImpl task, String message) {
        HBox notificationBox = new HBox();
        notificationBox.setStyle("-fx-background-color: #FFEB3B; -fx-padding: 10; -fx-spacing: 10;");

        ImageView notificationIcon = new ImageView();
        notificationIcon.setFitHeight(25);
        notificationIcon.setPreserveRatio(true);

        try {
            // Corrected path to icon
            notificationIcon.setImage(new Image(getClass().getResourceAsStream("@image/notification (1).png")));
        } catch (NullPointerException e) {
            System.out.println("Icon not found: /image/notification (1).png");
        }

        Text notificationMessage = new Text(message);
        Button markAsReadButton = new Button("Mark as Read");
        markAsReadButton.setOnAction(event -> {
            // Handle marking the notification as read
            notificationsContainer.getChildren().remove(notificationBox);
        });

        notificationBox.getChildren().addAll(notificationIcon, notificationMessage, markAsReadButton);
        return notificationBox;
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
        navigateTo("/miniProject/Statics.fxml"); // Navigate to the statistics view
    }

    @FXML
    private void handleNotificationsButton() {
        loadNotifications(); // Refresh notifications when the button is clicked
    }

    @FXML
    private void handleBackButton() {
        navigateTo("/miniProject/Display.fxml"); // Navigate back to the display view
    }

    private void navigateTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newView = loader.load();
            Stage currentStage = (Stage) buttonHome.getScene().getWindow(); // Get the current stage
            currentStage.setScene(new Scene(newView)); // Set the new scene
            currentStage.show(); // Show the new scene
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not load the requested view: " + fxmlFile + "\n" + e.getMessage());
        } catch (NullPointerException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "FXML file not found: " + fxmlFile);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleMarkAsRead(ActionEvent actionEvent) {
    }
}
